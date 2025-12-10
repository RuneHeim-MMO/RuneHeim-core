package org.zuma.examplePlugin.domain.droptable;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.quest.constants.QuestId;
import org.zuma.examplePlugin.events.droptable.DropTableRollEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class DropTable {
    private static final Random rand = new Random();
    private final List<DropTableEntry> drops;
    private final int id;
    private @Nullable
    final QuestId activeForQuest;

    public DropTable(int id, List<DropTableEntry> drops, @Nullable QuestId activeForQuest) {
        this.id = id;
        this.drops = drops;
        this.activeForQuest = activeForQuest;
    }

    public List<DropTableEntry> getDrops() {
        return drops;
    }

    @Nullable
    public ItemRecord roll(@Nullable RunePlayer player) {

        if (player != null) {
            // If this drop table is only active for a specific quest, check if the player has that quest active.
            if (this.activeForQuest != null && !player.getActiveQuestSteps().containsKey(this.activeForQuest.getId())) {
                return null;
            }

            AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.DROP_TABLES_ROLLED, 1);
        }

        for (DropTableEntry drop : drops) {
            boolean isMatch = rand.nextInt(drop.oneInNChance) == 0;

            if (!isMatch) {
                continue;
            }

            int amount = rand.nextInt(drop.max - drop.min + 1) + drop.min;

            if (drop.type == DropTableEntry.Type.ITEM) {
                ItemRecord result = new ItemRecord(drop.id, amount);

                Bukkit.getServer().getPluginManager().callEvent(new DropTableRollEvent(this, result));
                return result;
            }

            if (drop.type == DropTableEntry.Type.NESTED_DROP_TABLE) {
                DropTable matchingTable = RuneQuestPlugin.getDropTableHandler().getByID(drop.id);

                if (matchingTable == null) {
                    RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Drop table id " + id + " has rolled a nested drop table id " + drop.id + " but it is a null.");
                    return null;
                }


                for (int i = 0; i < amount; i++) {
                    ItemRecord currentRoll = matchingTable.roll(player);
                    Bukkit.getServer().getPluginManager().callEvent(new DropTableRollEvent(this, currentRoll));
                    if (currentRoll != null) return currentRoll;
                }
                return null;

            }
        }

        return null;

    }

    public int getId() {
        return id;
    }

    public void simulateRolls(RunePlayer player, int rolls) {
        HashMap<Integer, ItemRecord> results = new HashMap<>();

        for (int i = 0; i < rolls; i++) {
            ItemRecord result = this.roll(null);

            if (result != null) {
                if (results.containsKey(result.getItemId())) {
                    ItemRecord existing = results.get(result.getItemId());
                    results.put(existing.getItemId(), new ItemRecord(existing.getItemId(), existing.getAmount() + result.getAmount()));
                } else {
                    results.put(result.getItemId(), result);
                }
            }
        }

        RuneQuestPlugin.getRuneLogger().log(LogLevel.INFO, "Simulated " + rolls + " rolls on drop table ID " + this.id + ". Results:");
        player.asBukkitPlayer().sendMessage("§6Simulated " + rolls + " rolls on drop table ID " + this.id + ". Results:");
        results.values().forEach(itemRecord -> {
            AbstractItemType type = RuneQuestPlugin.getItemRegistry().getType(itemRecord.getItemId());

            if (type != null) {
                player.asBukkitPlayer().sendMessage(Component.text(type.displayName, TextColor.fromHexString(type.rarity.getHexColor())).append(Component.text(": x" + itemRecord.getAmount(), Color.SUCCESS.asColor())));
            }
        });
    }

    @Nullable
    public QuestId getActiveForQuest() {
        return activeForQuest;
    }
}
