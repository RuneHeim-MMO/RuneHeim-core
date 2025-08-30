package org.zuma.examplePlugin.domain.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.core.interfaces.InteractiveActionStrategy;
import org.zuma.examplePlugin.core.utils.*;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.hud.ActionBarManager;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.hud.PlayerScoreboardManager;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.party.Party;
import org.zuma.examplePlugin.domain.player.actions.ActionParams;
import org.zuma.examplePlugin.domain.player.handlers.DeathHandler;
import org.zuma.examplePlugin.domain.player.handlers.PlayerItemDistributor;
import org.zuma.examplePlugin.domain.player.handlers.PlayerXpHandler;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.domain.xp.PendingXPHandler;
import org.zuma.examplePlugin.events.player.RunePlayerHealthChangedEvent;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RunePlayer implements CombatEntity {
    private final UUID uuid;
    private final PlayerCombatData combatData;
    private @Nullable Party party;
    private final PendingXPHandler pendingXPHandler;

    private final EnumMap<ModifierType, Modifier> modifiers;
    private final EnumMap<SkillType, SkillModifier> skillModifiers;
    private final EnumMap<SkillType, Skill> skills;
    private final PlayerXpHandler xpHandler;
    private final PlayerItemDistributor itemHandler;
    private final ActionBarManager actionBarManager;
    private final PlayerScoreboardManager scoreboardManager;

    private long pouchCoins;
    private final List<InteractiveActionStrategy> pendingActions;
    private List<String> discoveredTerritoryIds;
    private Location getSafeLocation; // is set when player is is spectator mode.

    public RunePlayer(UUID uuid, EnumMap<SkillType, Skill> skills) {
        this.uuid = uuid;
        this.skills = skills;
        this.combatData = new PlayerCombatData(this, skills.get(SkillType.HITPOINTS).getBaseLevel(), Math.min(3, XpUtils.getCombatLevelFromSkills(skills)));

        this.modifiers = new EnumMap<>(ModifierType.class);
        this.skillModifiers = new EnumMap<>(SkillType.class);
        this.actionBarManager = new ActionBarManager(this);
        this.scoreboardManager = new PlayerScoreboardManager(this);
        this.itemHandler = new PlayerItemDistributor(this);
        this.xpHandler = new PlayerXpHandler(this);
        this.pendingXPHandler = new PendingXPHandler();

        this.pouchCoins = 0;
        this.pendingActions = new ArrayList<>();
        this.discoveredTerritoryIds = new ArrayList<>();

        HudService.updateVanillaGUI(this);
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

        // System.out.println("Recalculated skill modifiers " + newModifiers);
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
        return party;
    }

    public void setParty(@Nullable Party party) {
        this.party = party;
    }

    // Combat data (health, combat level)
    public int getHealth() {
        return combatData.getHealth();
    }

    public void setHealth(int hp, RunePlayerHealthChangedEvent.Cause cause) {
        combatData.setHealth(hp, cause);
    }

    public int getMaxHealth() {
        return skills.get(SkillType.HITPOINTS).getLevelWithBonuses();
    }

    public int getCombatLvl() {
        return combatData.getCombatLvl();
    }

    public void setCombatLvl(int combatLvl) {
        combatData.setCombatLvl(combatLvl);
    }

    public void registerHit(Hit hit) {
        this.pendingXPHandler.calculatePendingXP(hit);
        this.combatData.registerHit(hit);
    }

    // Busy state management
    public void startAction(InteractiveActionStrategy strategy, ActionParams params) {
        if (isBusy()) {
            HudService.showFailTitle(this, "You are already busy!");
            return;
        }

        InteractiveActionStrategy action = strategy.startAction(this, params);
        pendingActions.add(action);
    }

    public boolean isBusy() {
        return !this.pendingActions.isEmpty();
    }

    public void cancelActionTask(boolean interrupted) {
        if (interrupted && isBusy()) {
            this.asBukkitPlayer().showTitle(Title.title(
                    Component.empty(),
                    Component.text("Task interrupted.").color(Color.ERROR.asColor()),
                    TextUtils.getGenericDuration()
            ));
            SoundType.INTERRUPT_ACTION.play(this, false);
        }

        this.pendingActions.forEach(InteractiveActionStrategy::cancelTask);
        this.pendingActions.clear();
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

    public void onDeath(Hit lastHit) {
        DeathHandler.handlePlayerDeath(this, lastHit);

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
        getSafeLocation = safeLocation;
    }

    public void setSurvivalMode() {
        if (asBukkitPlayer().getGameMode() != GameMode.SPECTATOR) {
            return;
        }

        asBukkitPlayer().setGameMode(GameMode.SURVIVAL);
        getSafeLocation = null;
    }

    public Location getGetSafeLocation() {
        return getSafeLocation;
    }

    public boolean isInCombat() {
        return this.combatData.isInCombat();
    }

    public void onHitLand(Hit hit) {
        if (hit.damage() > 0 && !hit.isSpecialAttack()) {
            combatData.recoverSpecialEnergy();
        }
    }

    public boolean isSpecialAttacking() {
        return asBukkitPlayer().isSneaking() && this.combatData.canPerformSpecialAttack();
    }

    public void onSpecialAttack(Hit hit) {
        this.combatData.performSpecialAttack();

        Entity defender = hit.defender().getEntity();

        if (defender != null) {
            defender.setVelocity(defender.getVelocity().add(new Vector(0, 0.4, 0)));
        }
    }

    public int getSpecialEnergyPercent() {
        return (int) (this.combatData.getSpecialEnergy() * 100);
    }
}
