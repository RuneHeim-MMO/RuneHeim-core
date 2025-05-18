package org.zuma.examplePlugin.core.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtils {

    public static ItemStack getItemStackInHand(Player player) {
        return player.getInventory().getItemInMainHand();
    }
}
