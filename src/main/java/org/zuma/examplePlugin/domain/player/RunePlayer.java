package org.zuma.examplePlugin.domain.player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.constants.XpSource;
import org.zuma.examplePlugin.core.interfaces.SkillRestrictable;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.player.states.RunePlayerBusyStateHandler;
import org.zuma.examplePlugin.domain.skill.Skill;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.player.RunePlayerGainedXPEvent;

import java.util.*;
import java.util.function.Consumer;

public class RunePlayer {
    private final UUID uuid;
    private final List<Modifier> gearModifiers = new ArrayList<>();
    private final EnumMap<SkillType, Skill> skills = new EnumMap<>(SkillType.class);
    private final RunePlayerBusyStateHandler busyStateHandler;

    public RunePlayer(UUID uuid) {
        this.uuid = uuid;
        this.busyStateHandler = new RunePlayerBusyStateHandler(this);

        // Each player starts with default XP in all skill types.
        for (SkillType type : SkillType.values()) {
            skills.put(type, new Skill(type, type.getDefaultXp()));
        }
    }

    public UUID getUUID() {
        return uuid;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public void setGearModifiers(List<Modifier> newModifiers) {
        gearModifiers.clear();
        gearModifiers.addAll(newModifiers);

        System.out.println("Recalculated modifiers " + newModifiers);
    }

    public List<Modifier> getActiveModifiers() {
        return List.copyOf(gearModifiers);
    }

    protected void setSkill(SkillType type, Skill skill) {
        skills.put(type, skill);
    }

    @NotNull
    public Skill getSkill(SkillType type) {
        return skills.get(type);
    }

    @NotNull
    public Collection<Skill> getSkills() {
        return skills.values();
    }

    public void addSkillXp(SkillType type, int xp, XpSource source) {
        Skill skill = skills.get(type);

        RunePlayerGainedXPEvent event = new RunePlayerGainedXPEvent(this, type, xp, source);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        if (skill == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Tried adding XP for skill " + type.getLabel() + " which does not exist in players instance.");
            return;
        }

        skill.addXp(xp);

    }


    public void showFailTitle(String text) {
        this.getPlayer().showTitle(Title.title(
                Component.text(""),
                Component.text(text).color(Colors.ERROR.asColor()),
                TextUtils.getGenericDuration()
        ));
        SoundType.FAIL_ACTION.play(this.getPlayer());
    }

    public void showFailedMessage(Component text) {
        getPlayer().sendMessage(text.color(Colors.ERROR.asColor()));
        SoundType.FAIL_ACTION.play(this.getPlayer());
    }

    public void useBusyState(int durationMs, String action, String successAction, SoundType stepSound, SoundType successSound, Consumer<Void> consumer) {
        this.busyStateHandler.useBusyState(durationMs, action, successAction, stepSound, successSound, consumer);
    }

    public boolean isBusy() {
        return busyStateHandler.isBusy();
    }

    public void cancelTask() {
        busyStateHandler.cancelTask();
    }

    public boolean meetsRequirements(Object item) {
        if (item instanceof SkillRestrictable) {
            List<SkillRequirement> requirements = ((SkillRestrictable) item).getRequirements();
            return requirements.stream().allMatch(req -> req.playerMeetsRequirement(this));

        }
        return true;
    }

    public void addItemOrDrop(ItemRecord item) {
        AbstractItem customItem = RuneQuestPlugin.getItemRegistry().getItem(item.getItemId());

        if (customItem == null) {
            RuneQuestPlugin.getRuneLogger().logWithStack(LogLevel.WARN, "Cannot add item with id " + item.getItemId() + " to players inventory. Item does not exist in registry.");
            return;
        }


        ItemStack itemStack = customItem.asItemStack(item.getAmount());
        Component name = itemStack.getItemMeta().displayName();
        String plainName = name != null ? PlainTextComponentSerializer.plainText().serialize(name) : null;

        addItemOrDrop(itemStack, "Your inventory is full! " + itemStack.getAmount() + "x " + plainName + " dropped on the ground.");
    }

    public void addItemOrDrop(ItemStack item, String dropMessage) {
        Player player = this.getPlayer();
        HashMap<Integer, ItemStack> leftovers = player.getInventory().addItem(item);


        if (!leftovers.isEmpty()) {
            Location dropLocation = player.getLocation();
            leftovers.values().forEach(leftover ->
                    player.getWorld().dropItemNaturally(dropLocation, leftover)
            );

            if (dropMessage != null && !dropMessage.isEmpty()) {
                player.sendMessage(Component.text(dropMessage).color(Colors.ERROR.asColor()));
                SoundType.INVENTORY_FULL.play(player);
            }

            return;
        }

        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 1f, 1f);
    }
}
