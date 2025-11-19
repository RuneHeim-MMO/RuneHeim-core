package org.zuma.examplePlugin.domain.player;


import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.permissions.roles.BaseRole;
import org.zuma.examplePlugin.core.permissions.PlayerRole;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.core.interfaces.InteractiveActionStrategy;
import org.zuma.examplePlugin.core.utils.*;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.hud.ActionBarManager;
import org.zuma.examplePlugin.domain.hud.BossBarManager;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.hud.PlayerScoreboardManager;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.party.Party;
import org.zuma.examplePlugin.domain.payments.SupporterLevel;
import org.zuma.examplePlugin.domain.player.actions.ActionParams;
import org.zuma.examplePlugin.domain.player.handlers.*;
import org.zuma.examplePlugin.domain.player.settings.PlayerSettings;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.domain.xp.PendingXPHandler;
import org.zuma.examplePlugin.events.player.RunePlayerHealthChangedEvent;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RunePlayer implements CombatEntity {
    private static final double HP_SKILL_XP_PER_DMG_POINT_RECEIVED = 5;
    private @Nullable UUID partyUUID;
    private final UUID uuid;

    private PlayerRole role;
    private PermissionAttachment permissionAttachment;

    private final PlayerCombatData combatData;
    private PlayerSettings settings;
    private PlayerBombsContainer bombsContainer;

    private final PendingXPHandler pendingXPHandler;
    private final EnumMap<ModifierType, Modifier> modifiers;
    private final EnumMap<SkillType, SkillModifier> skillModifiers;
    private List<TemporaryEffect> activeEffects; // Potions, etc
    private final EnumMap<SkillType, Skill> skills;
    private final PlayerXpHandler xpHandler;
    private final PlayerItemDistributor itemHandler;
    private final ActionBarManager actionBarManager;
    private final BossBarManager bossBarManager;
    private final BusyStateHandler busyStateHandler;
    private final PlayerScoreboardManager scoreboardManager;
    private final ScoreboardUsernameRenderer scoreboardUsernameRenderer;

    private long pouchCoins;
    private List<String> discoveredTerritoryIds, completedQuestIds;
    private boolean isInCutscene, isDead, isInDialog, isDialogOptionChoosing, isNextMessageMegaphone;
    private HashMap<String, AbstractQuestStep> activeQuestSteps; // Quest id : step
    private @Nullable Location safeLocation; // is set when player is in spectator/cutscene mode.
    private @Nullable String trackedQuestId;

    public RunePlayer(UUID uuid, EnumMap<SkillType, Skill> skills) {
        this.uuid = uuid;
        this.role = new PlayerRole(BaseRole.PLAYER, null, SupporterLevel.LVL_0);
        this.skills = skills;
        this.combatData = new PlayerCombatData(this, skills.get(SkillType.HITPOINTS).getBaseLevel(), Math.min(3, XpUtils.getCombatLevelFromSkills(skills)), 1, 1);

        this.bossBarManager = new BossBarManager(this);
        this.activeQuestSteps = new HashMap<>();
        this.modifiers = new EnumMap<>(ModifierType.class);
        this.skillModifiers = new EnumMap<>(SkillType.class);
        this.actionBarManager = new ActionBarManager(this);
        this.busyStateHandler = new BusyStateHandler(this);
        this.scoreboardManager = new PlayerScoreboardManager(this);
        this.itemHandler = new PlayerItemDistributor(this);
        this.xpHandler = new PlayerXpHandler(this);
        this.pendingXPHandler = new PendingXPHandler();
        this.scoreboardUsernameRenderer = new ScoreboardUsernameRenderer(this);
        this.settings = new PlayerSettings();

        this.pouchCoins = 0;
        this.discoveredTerritoryIds = new ArrayList<>();
        this.completedQuestIds = new ArrayList<>();
        this.isDead = false;
        this.isInDialog = false;
        this.activeEffects = new ArrayList<>();
        this.bombsContainer = new PlayerBombsContainer(0, 0, 0, 0, 0, 0);
        this.trackedQuestId = null;

        Bukkit.getScheduler().runTask(RuneQuestPlugin.getInstance(), () -> {
            this.permissionAttachment = asBukkitPlayer().addAttachment(RuneQuestPlugin.getInstance());
            this.scoreboardUsernameRenderer.updateTabUsername();
            HudService.updateVanillaGUI(this);
        });
    }

    public UUID getUUID() {
        return uuid;
    }

    public Player asBukkitPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public Entity getEntity() {
        return this.asBukkitPlayer();
    }

    // Modifiers
    public void setModifiers(List<Modifier> newModifiers) {
        modifiers.clear();
        modifiers.putAll(newModifiers.stream()
                .collect(Collectors.toMap(Modifier::type, Function.identity())));
        // System.out.println("Recalculated modifiers " + newModifiers);
    }

    public void setSkillModifiers(List<SkillModifier> newModifiers) {
        skills.values().forEach(skill -> skill.setBonus(0));
        skillModifiers.clear();
        skillModifiers.putAll(newModifiers.stream()
                .collect(Collectors.toMap(SkillModifier::type, Function.identity())));

        skillModifiers.values().forEach(skillModifier -> {
            Skill matchingSkill = skills.get(skillModifier.type());

            if (matchingSkill == null) {
                return;
            }

            matchingSkill.setBonus(skillModifier.amount());
        });
    }

    public EnumMap<ModifierType, Modifier> getModifiers() {
        return new EnumMap<>(modifiers);
    }

    // Skills
    @NotNull
    public Skill getSkill(SkillType type) {
        return skills.get(type);
    }

    public EnumMap<SkillType, Skill> getSkills() {
        return new EnumMap<>(skills);
    }

    public void setSkills(EnumMap<SkillType, Skill> skills) {
        this.skills.putAll(skills);
        combatData.setCombatLvl(Math.max(3, XpUtils.getCombatLevelFromSkills(this.skills)));
        HudService.updateVanillaGUI(this);
    }

    // Party
    public @Nullable Party getParty() {
        if (partyUUID != null) {
            return RuneQuestPlugin.getPartyManager().getPartyByUUID(partyUUID);
        }

        return null;
    }

    public void setParty(@Nullable Party party) {
        this.partyUUID = party != null ? party.getUuid() : null;
    }

    // Combat data (health, combat level)
    public int getHealth() {
        return combatData.getHealth();
    }

    public void setHealth(int hp, RunePlayerHealthChangedEvent.Cause cause) {
        combatData.setHealth(hp, cause);
        HudService.updateVanillaGUI(this);
    }

    public int getMaxHealth() {
        return skills.get(SkillType.HITPOINTS).getLevelWithBonuses();
    }

    public int getCombatLvl() {
        return combatData.getCombatLvl();
    }

    public void setCombatLvl(int combatLvl) {
        combatData.setCombatLvl(combatLvl);
        this.scoreboardUsernameRenderer.updateTabUsername();
    }

    public void registerHit(Hit hit) {
        this.pendingXPHandler.registerHit(hit);
        this.combatData.registerHit(hit);

        if (hit.getDamage() > 0) {
            this.addXp(SkillType.HITPOINTS, hit.getDamage() * HP_SKILL_XP_PER_DMG_POINT_RECEIVED, XpSource.PERSONAL);
        }

        if (this.hasPendingActions()) {
            this.cancelActionTask(true);
        }

    }

    // Busy state management
    public boolean queueOrStartAction(InteractiveActionStrategy strategy, ActionParams params, boolean stackable) {
        return busyStateHandler.queueOrStartAction(strategy, params, stackable);
    }

    public boolean hasPendingActions() {
        return this.busyStateHandler.hasPendingActions();
    }

    public void cancelActionTask(boolean interrupted) {
        this.busyStateHandler.cancelActionTask(interrupted);
    }

    public int getQueuedTasks() {
        return this.busyStateHandler.getQueuedTasks();
    }

    // XP handling
    public void distributeXp(SkillType type, double xp) {
        xpHandler.distributeXp(type, xp);
    }

    public void addXp(SkillType type, double xp, XpSource source) {
        xpHandler.addXp(type, xp, source);
    }

    @Nullable
    public SkillType getLastXPGainedSkillType() {
        if (xpHandler == null) {
            return null;
        }

        return xpHandler.getLastXPGainedSkillType();
    }

    // Item handling
    public void giveItemOrDrop(ItemRecord item) {
        itemHandler.addItemOrDrop(item);
    }

    public void giveItemOrDrop(ItemStack item) {
        itemHandler.addItemOrDrop(item);
    }

    // Utility
    public boolean meetsRequirements(Object item) {
        return PlayerUtils.playerMeetsItemRequirements(this, item);
    }

    public ActionBarManager getActionBar() {
        return this.actionBarManager;
    }

    public long getPouchCoins() {
        return pouchCoins;
    }

    public void setPouchCoins(long coins, boolean silent) {
        if (!silent)
            SoundType.COINS_DROP.play(this.asBukkitPlayer(), true);

        this.pouchCoins = coins;
    }

    public void killPlayer(@Nullable Hit lastHit) {
        DeathHandler.killPlayer(this, lastHit);

        this.pendingXPHandler.distributePendingXP();
    }

    public List<String> getDiscoveredTerritoryIds() {
        return discoveredTerritoryIds;
    }

    public void setDiscoveredTerritoryIds(List<String> list) {
        this.discoveredTerritoryIds = list;
    }

    public void setSpectatorMode(Location safeLocation) {
        asBukkitPlayer().setGameMode(GameMode.SPECTATOR);
        this.safeLocation = safeLocation;
    }

    public void setSurvivalMode() {
        if (asBukkitPlayer().getGameMode() != GameMode.SPECTATOR) {
            return;
        }

        asBukkitPlayer().setGameMode(GameMode.SURVIVAL);
        safeLocation = null;
    }

    public void setInCutscene(Location safeLocation) {
        this.isInCutscene = true;
        this.safeLocation = safeLocation;
    }

    public void setOutOfCutscene() {
        this.isInCutscene = false;
        safeLocation = null;
    }

    @Nullable
    public Location getSafeLocation() {
        return safeLocation;
    }

    public boolean isInCombat() {
        return this.combatData.isInCombat();
    }

    public void onHitLand(Hit hit) {
        if (hit.getDamage() > 0 && !hit.isSpecialAttack()) {
            combatData.recoverSpecialEnergy();
        }
    }

    public boolean isSpecialAttacking() {
        return asBukkitPlayer().isSneaking() && this.combatData.canPerformSpecialAttack();
    }

    public boolean canPerformSpecialAttack() {
        return this.combatData.canPerformSpecialAttack();
    }

    public void onSpecialAttack(Hit hit) {
        this.combatData.performSpecialAttack();

        Entity defender = hit.getDefender().getEntity();

        if (defender != null) {
            defender.setVelocity(defender.getVelocity().add(new Vector(0, 0.4, 0)));
        }
    }

    public int getSpecialEnergyPercent() {
        return (int) (this.combatData.getSpecialEnergy() * 100);
    }

    public void setSpecialEnergyPercent(int percent) {
        this.combatData.setSpecialEnergy(percent);
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void setPrayerEnergy(double amount) {
        this.combatData.setPrayerEnergy(amount);
    }

    public double getPrayerEnergy() {
        return this.combatData.getPrayerEnergy();
    }

    public HashMap<String, AbstractQuestStep> getActiveQuestSteps() {
        return activeQuestSteps;
    }

    public void setActiveQuestSteps(HashMap<String, AbstractQuestStep> activeQuestSteps) {
        this.activeQuestSteps = activeQuestSteps;

        if (this.trackedQuestId != null) {
            AbstractQuestStep trackedStep = activeQuestSteps.get(this.trackedQuestId);

            if (trackedStep != null) {
                this.bossBarManager.setText(trackedStep.getDescription());
                this.bossBarManager.setProgress(trackedStep.getProgressPercentage());
            } else {
                this.trackedQuestId = null; // No longer tracking a valid quest
                this.bossBarManager.setDefault();
            }
        }
    }

    public List<String> getCompletedQuestIds() {
        return completedQuestIds;
    }

    public void setCompletedQuestIds(List<String> completedQuestIds) {
        this.completedQuestIds = completedQuestIds;
    }


    public boolean isDialogOptionChoosing() {
        return isDialogOptionChoosing;
    }

    public void setDialogOptionChoosing(boolean dialogOptionChoosing) {
        isDialogOptionChoosing = dialogOptionChoosing;
    }

    public boolean isInCutscene() {
        return isInCutscene;
    }

    public List<TemporaryEffect> getActiveEffects() {
        return activeEffects;
    }

    public boolean hasActiveEffect(String grantedByKey) {
        return activeEffects.stream()
                .anyMatch(effect -> effect.getGrantedBy() != null
                        && effect.getGrantedBy().equals(grantedByKey)
                        && !effect.isExpired());
    }

    public void setActiveEffects(List<TemporaryEffect> effects) {
        if (effects == null) return;
        this.activeEffects = effects;
    }

    public void addActiveEffect(TemporaryEffect effect) {
        this.activeEffects.add(effect);
    }

    public PlayerRole getRole() {
        return role;
    }

    public void setRole(PlayerRole role, boolean reload) {
        this.role = role;

        if (reload) {
            role.reloadPermissions(this);
        }
    }

    public PermissionAttachment getPermissionAttachment() {
        return permissionAttachment;
    }

    public PlayerScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public void reloadNametags() {
        this.scoreboardManager.reloadNametags();
    }

    public ScoreboardUsernameRenderer getScoreboardUsernameRenderer() {
        return scoreboardUsernameRenderer;
    }

    public void setPermissionAttachment(PermissionAttachment permissionAttachment) {
        this.permissionAttachment = permissionAttachment;
    }

    public void setSupporterLevel(SupporterLevel supporterLevel) {
        this.role = new PlayerRole(this.role.getBaseRole(), this.role.getUpgradedRole(), supporterLevel);
    }

    public PlayerBombsContainer getBombsContainer() {
        return bombsContainer;
    }

    public void setBombsContainer(PlayerBombsContainer bombsContainer) {
        this.bombsContainer = bombsContainer;
    }

    public boolean isNextMessageMegaphone() {
        return isNextMessageMegaphone;
    }

    public void setNextMessageMegaphone(boolean nextMessageMegaphone) {
        isNextMessageMegaphone = nextMessageMegaphone;
    }

    public PlayerSettings getSettings() {
        return settings;
    }

    public void setSettings(PlayerSettings settings) {
        this.settings = settings;
    }

    public BossBarManager getBossBarManager() {
        return bossBarManager;
    }

    public @Nullable String getTrackedQuestId() {
        return trackedQuestId;
    }

    public void setTrackedQuestId(@Nullable String trackedQuestId) {
        this.trackedQuestId = trackedQuestId;

        if (trackedQuestId != null) {
            AbstractQuestStep matchingStep = this.activeQuestSteps.get(trackedQuestId);

            if (matchingStep != null) {
                this.bossBarManager.setText(matchingStep.getDescription());
                this.bossBarManager.setProgress(matchingStep.getProgressPercentage());
            }
            return;
        }

        this.bossBarManager.setDefault();
    }

    public boolean isInDialog() {
        return isInDialog;
    }

    public void setInDialog(boolean inDialog) {
        isInDialog = inDialog;
    }
}
