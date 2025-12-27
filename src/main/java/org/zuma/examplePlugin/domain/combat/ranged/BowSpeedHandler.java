package org.zuma.examplePlugin.domain.combat.ranged;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.impl.equipable.RangedWeapon;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class BowSpeedHandler implements Listener {

    public BowSpeedHandler() {

    }


    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerShootBow(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player shooter)) {
            return;
        }

        ItemStack inHandStack = event.getBow();

        if (inHandStack == null || inHandStack.getType().isAir()) return;

        AbstractItemType itemType = RuneQuestPlugin.getItemRegistry().getType(inHandStack);
        if (itemType == null) return;

        RunePlayer player = RuneQuestPlugin.getPlayerManager().getRunePlayer(shooter);
        if (player == null) return;

        float drawPower = event.getForce();

        if (!(itemType instanceof RangedWeapon bow)) {
            return;
        }

        if (drawPower < bow.getSpeed().getRangedMinPower()) {
            event.setCancelled(true);
            player.asBukkitPlayer().sendMessage(Component.text("You need to draw the bow further!", Color.ERROR.asColor()));
            SoundType.BOW_SHOT_TOO_EARLY.play(player, true);
        }

    }


}
