package org.zuma.examplePlugin.domain.player.inventory;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.gui.impl.individual.FusionMasterInventory;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.constants.ItemType;

import javax.annotation.Nullable;
import java.util.List;

public class PlayerInvTopRowHandler implements Listener {
    private final int PET_SLOT = 0;
    private final int AMULET_SLOT = 1;
    private final int RING_1_SLOT = 2;
    private final int RING_2_SLOT = 3;
    private final int FAIRY_SLOT = 4;
    private final int FAIRY_FOOD_SLOT = 5;

    private enum ItemStackType {
        PET, AMULET, RING, FAIRY, FAIRY_FOOD
    }


    private boolean isValidItemStack(ItemStackType type, @Nullable ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }

        AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getItem(itemStack);

        if (itemType == null) {
            return false;
        }

        return switch (type) {
            case PET -> false;
            case AMULET -> false;
            case RING -> false;
            case FAIRY -> false;
            case FAIRY_FOOD -> false;
        };

    }

}
