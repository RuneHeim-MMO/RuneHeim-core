package org.zuma.examplePlugin.domain.mob.meta;

import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.domain.mob.EquipmentSetRecord;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSlot;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public record MobEquipmentMeta(List<EquipmentSetRecord> items) {
    private static final Random RANDOM = new Random();

    @Nullable
    public ItemStack rollSlot(EquipmentSlot slot) {
        return items.stream()
                .filter(record -> record.slot() == slot)
                .filter(record -> RANDOM.nextInt(100) < record.chance())
                .map(EquipmentSetRecord::stack)
                .findFirst()
                .orElse(null);
    }

}
