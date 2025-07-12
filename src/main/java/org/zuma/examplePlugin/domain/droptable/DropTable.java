package org.zuma.examplePlugin.domain.droptable;

import org.zuma.examplePlugin.domain.item.ItemRecord;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class DropTable {
    private static final Random rand = new Random();
    private final List<DropItemEntry> drops;
    private final int id;

    public DropTable(int id, List<DropItemEntry> drops) {
        this.id = id;
        this.drops = drops;
    }

    @Nullable
    public ItemRecord roll() {

        for (DropItemEntry drop : drops) {
            boolean isMatch = rand.nextInt(drop.oneInNChance()) == 0;

            if (!isMatch) {
                continue;
            }

            int amount = rand.nextInt(drop.max() - drop.min() + 1) + drop.min();

            return new ItemRecord(drop.itemId(), amount);
        }

        return null;

    }

    public int getId() {
        return id;
    }
}
