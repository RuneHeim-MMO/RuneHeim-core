package org.zuma.examplePlugin.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.interfaces.WithRequirements;
import org.zuma.examplePlugin.item.SkillRequirement;
import org.zuma.examplePlugin.item.base.CustomItem;
import org.zuma.examplePlugin.player.RunePlayer;

import java.util.List;

public class PlayerUtils {

    public static ItemStack getItemStackInHand(Player player) {
        return player.getInventory().getItemInMainHand();
    }

    public static boolean playerMeetsRequirements(RunePlayer player, CustomItem item) {
        if (item instanceof WithRequirements) {
            List<SkillRequirement> requirements = ((WithRequirements) item).getRequirements();
            return requirements.stream().allMatch(req -> req.playerMeetsRequirement(player));

        }
        return true;
    }
}
