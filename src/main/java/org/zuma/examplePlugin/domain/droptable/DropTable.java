package org.zuma.examplePlugin.domain.droptable;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.events.droptable.DropTableRollEvent;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class DropTable {
    private static final Random rand = new Random();
    private final List<DropTableEntry> drops;
    private final int id;

    public DropTable(int id, List<DropTableEntry> drops) {
        this.id = id;
        this.drops = drops;
    }

    @Nullable
    public ItemRecord roll() {

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
                    ItemRecord currentRoll = matchingTable.roll();
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
}
