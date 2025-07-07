package org.zuma.examplePlugin.domain.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.core.utils.XpUtils;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.hud.ActionBarManager;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.hud.PlayerScoreboardManager;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.SkillModifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.party.Party;
import org.zuma.examplePlugin.domain.player.handlers.PlayerItemDistributor;
import org.zuma.examplePlugin.domain.player.handlers.PlayerXpHandler;
import org.zuma.examplePlugin.domain.player.states.RunePlayerBusyStateHandler;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.player.RunePlayerHealthChangedEvent;

import java.util.EnumMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RunePlayer implements CombatEntity {
    private final UUID uuid;
    private final PlayerCombatData combatData;
    private @Nullable Party party;

    private final EnumMap<ModifierType, Modifier> modifiers;
    private final EnumMap<SkillType, SkillModifier> skillModifiers;
    private final EnumMap<SkillType, Skill> skills;
    private final PlayerXpHandler xpHandler;
    private final PlayerItemDistributor itemHandler;
    private final RunePlayerBusyStateHandler busyStateHandler;
    private final ActionBarManager actionBarManager;
    private final PlayerScoreboardManager scoreboardManager;

    private long pouchCoins;


    public RunePlayer(UUID uuid, EnumMap<SkillType, Skill> skills) {
        this.uuid = uuid;
        this.skills = skills;
        this.combatData = new PlayerCombatData(this, skills.get(SkillType.HITPOINTS).getBaseLevel(), XpUtils.getCombatLevelFromSkills(skills));

        modifiers = new EnumMap<>(ModifierType.class);
        skillModifiers = new EnumMap<>(SkillType.class);
        actionBarManager = new ActionBarManager(this);
        scoreboardManager = new PlayerScoreboardManager(this);
        busyStateHandler = new RunePlayerBusyStateHandler(this);
        itemHandler = new PlayerItemDistributor(this);
        xpHandler = new PlayerXpHandler(this);

        pouchCoins = 0;

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
        System.out.println("Recalculated modifiers " + newModifiers);
    }

    public void setSkillModifiers(List<SkillModifier> newModifiers) {
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

        System.out.println("Recalculated skill modifiers " + newModifiers);
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
        combatData.setCombatLvl(XpUtils.getCombatLevelFromSkills(skills));
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
        return skills.get(SkillType.HITPOINTS).getBaseLevel();
    }

    public int getCombatLvl() {
        return combatData.getCombatLvl();
    }

    public void setCombatLvl(int combatLvl) {
        combatData.setCombatLvl(combatLvl);
    }

    public void takeHit(Hit hit) {
        combatData.takeHit(hit);
    }

    // Busy state management
    public void useBusyState(int durationMs, String action, String successAction, SoundType stepSound, SoundType successSound, Runnable onSuccess, @Nullable Runnable onFailure) {
        this.busyStateHandler.useBusyState(durationMs, action, successAction, stepSound, successSound, onSuccess, onFailure);
    }

    public boolean isBusy() {
        return busyStateHandler.isBusy();
    }

    public void cancelBusyState() {
        busyStateHandler.cancelTask();
    }

    // XP handling
    public void distributeXp(SkillType type, double xp) {
        xpHandler.distributeXp(type, xp);
    }

    public void addXp(SkillType type, double xp, XpSource source) {
        xpHandler.addXp(type, xp, source);
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

    public void setPouchCoins(long coins) {
        SoundType.COINS_DROP.play(this.asBukkitPlayer(), false);
        this.pouchCoins = coins;
    }


}
