package org.zuma.examplePlugin.domain.quest;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.interfaces.Restrictable;
import org.zuma.examplePlugin.domain.effect.EffectHandler;
import org.zuma.examplePlugin.domain.effect.effects.QuestCompletedEffect;
import org.zuma.examplePlugin.domain.effect.effects.QuestStartEffect;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.base.AbstractQuestStep;
import org.zuma.examplePlugin.domain.quest.constants.QuestDifficulty;
import org.zuma.examplePlugin.domain.quest.constants.QuestDuration;
import org.zuma.examplePlugin.domain.quest.constants.QuestStartFailReason;
import org.zuma.examplePlugin.domain.quest.constants.QuestStatus;
import org.zuma.examplePlugin.domain.quest.impl.CollectionStep;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class Quest implements Restrictable {
    private final String id, name;
    private final List<AbstractQuestStep> steps;
    private final QuestDifficulty difficulty;
    private final boolean silent; // If true, no start/completion effects or messages - used for internal tracking of player state.
    private QuestDuration duration;
    private QuestReward reward;

    // Starting conditions
    private List<SkillRequirement> requirements;
    private List<String> requiredCompletedQuests;

    public Quest(String id, String name, QuestDifficulty difficulty, boolean silent) {
        this.id = id;
        this.name = name;
        this.steps = new ArrayList<>();
        this.difficulty = difficulty;
        this.silent = silent;
        this.duration = QuestDuration.SHORT;
        this.requirements = new ArrayList<>();
        this.requiredCompletedQuests = new ArrayList<>();
        this.reward = new QuestReward(null, null, null);
    }

    public Quest setRewards(QuestReward reward) {
        this.reward = reward;
        return this;
    }

    public Quest setRequiredCompletedQuests(List<String> questIds) {
        this.requiredCompletedQuests = questIds;
        return this;
    }

    public Quest setRequirements(List<SkillRequirement> requirements) {
        this.requirements = requirements;
        return this;
    }

    public Quest addSteps(List<AbstractQuestStep> steps) {
        for (AbstractQuestStep step : steps) {
            int stepIndex = this.steps.size();
            step.setId(stepIndex);
            this.steps.add(step);
        }

        this.duration = getQuestDuration();
        return this;
    }

    public String getId() {
        return id;
    }

    public List<AbstractQuestStep> getSteps() {
        return steps;
    }

    public String getName() {
        return name;
    }

    public QuestStatus getPlayerQuestStatus(RunePlayer player) {
        if (player.getActiveQuestSteps().containsKey(this.getId())) {
            return QuestStatus.STARTED;
        }

        if (player.getCompletedQuestIds().contains(this.id)) {
            return QuestStatus.COMPLETED;
        }

        if (!new HashSet<>(player.getCompletedQuestIds()).containsAll(this.requiredCompletedQuests) || !player.meetsRequirements(this)) {
            return QuestStatus.NOT_MEETING_REQUIREMENTS;
        }

        return QuestStatus.NOT_STARTED;
    }

    private QuestDuration getQuestDuration() {
        int size = steps.size();

        if (size <= 10) {
            return QuestDuration.SHORT;
        } else if (size <= 15) {
            return QuestDuration.MEDIUM;
        } else if (size <= 30) {
            return QuestDuration.LONG;
        } else {
            return QuestDuration.VERY_LONG;
        }
    }

    public ItemStack getDisplayItemStack(RunePlayer player) {
        QuestStatus status = getPlayerQuestStatus(player);

        ItemStack baseItem = switch (status) {
            case STARTED -> new ItemStack(Material.WRITABLE_BOOK);
            case COMPLETED -> new ItemStack(Material.ENCHANTED_BOOK);
            case NOT_STARTED -> new ItemStack(Material.BOOK);
            case NOT_MEETING_REQUIREMENTS -> new ItemStack(Material.BARRIER);
        };

        ItemMeta meta = baseItem.getItemMeta();
        List<Component> lore = new ArrayList<>();

        meta.displayName(Component.text(this.getName(), NamedTextColor.YELLOW).decoration(TextDecoration.ITALIC, false));

        switch (status) {
            case STARTED -> {
                AbstractQuestStep step = player.getActiveQuestSteps().get(this.id);
                if (step != null && step.getDescription() != null) {
                    lore.add(Component.text(step.getDescription(), NamedTextColor.WHITE));
                }

                if (step instanceof CollectionStep collectionStep) {
                    if (!collectionStep.getRequiredItems().isEmpty()) lore.add(Component.empty());

                    collectionStep.getRequiredItems().forEach((req) -> {
                        AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getType(req.getItemId());
                        if (itemType == null) return;
                        boolean collected = collectionStep.getAcquiredItems().get(itemType.id) >= req.getAmount();

                        lore.add(Component.text(itemType.displayName + ": ", Color.ACCENT.asColor())
                                .append(Component.text(collectionStep.getAcquiredItems().get(itemType.id) + "/" + req.getAmount(), collected ? Color.SUCCESS.asColor() : Color.ERROR_LIGHT.asColor())));
                    });

                }
            }
            case NOT_STARTED -> lore.add(Component.text("Not started", Color.ERROR_LIGHT.asColor()));
            case NOT_MEETING_REQUIREMENTS -> lore.add(Component.text("Requirements not met", Color.ERROR.asColor()));
            case COMPLETED -> lore.add(Component.text("Completed", Color.SUCCESS.asColor()));
        }

        lore.add(Component.empty());
        lore.add(Icon.CONTRACT_SKULL.asComponent().append(Component.text("Difficulty: ", Color.ITEM_LORE_DESCRIPTION.asColor()).append(Component.text(this.difficulty.getLabel()))));
        lore.add(Icon.CLOCK.asComponent().append(Component.text("Duration: ", Color.ITEM_LORE_DESCRIPTION.asColor()).append(Component.text(this.duration.getLabel()))));

        meta.lore(lore.stream().map(c -> c.decoration(TextDecoration.ITALIC, false)).toList());

        baseItem.setItemMeta(meta);
        return baseItem;

    }

    @Nullable
    public QuestStartFailReason getQuestStartFailReason(RunePlayer player) {
        if (!player.meetsRequirements(this)) {
            return QuestStartFailReason.DOES_NOT_MEET_REQUIREMENTS;
        }

        if (player.getCompletedQuestIds().contains(id)) {
            return QuestStartFailReason.ALREADY_COMPLETED;
        }

        if (player.getActiveQuestSteps().containsKey(id)) {
            return QuestStartFailReason.ALREADY_STARTED;
        }

        return null;
    }

    public void startForPlayer(RunePlayer player) throws IllegalStateException {
        QuestStartFailReason failReason = getQuestStartFailReason(player);

        if (failReason != null) {
            throw new IllegalStateException("Cannot start quest " + this.id + " for player " + player.getUUID() + ". Reason: " + failReason);
        }

        if (steps.isEmpty() || steps.getFirst() == null) {
            handleCompletion(player);
            return;
        }

        if (!this.silent) {
            EffectHandler.playAt(player.asBukkitPlayer().getLocation(), new QuestStartEffect(player, this));
            HudService.sendQuestStartMessage(player, this);
            HudService.sendQuestStepTaskMessage(player, steps.getFirst());
        }

        player.getActiveQuestSteps().put(id, steps.getFirst().cloneForPlayer(player.getUUID()));
    }

    public void handleCompletion(RunePlayer player) {
        player.getCompletedQuestIds().add(id);
        player.getActiveQuestSteps().remove(id);
        reward.applyRewards(player);

        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            if (player.asBukkitPlayer() == null || !player.asBukkitPlayer().isOnline()) return;

            if (!this.silent) {
                EffectHandler.playAt(player.asBukkitPlayer().getLocation(), new QuestCompletedEffect(player, this));
                HudService.sendQuestCompletedMessage(player, this);
                HudService.sendQuestRewards(player, this);
            }
        }, 60L);
    }


    public QuestDifficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public List<SkillRequirement> getSkillRequirements() {
        return requirements;
    }

    @Override
    public List<String> getRequiredCompletedQuests() {
        return requiredCompletedQuests;
    }

    public QuestReward getReward() {
        return reward;
    }

    public boolean isSilent() {
        return silent;
    }
}
