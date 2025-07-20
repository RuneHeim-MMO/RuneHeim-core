package org.zuma.examplePlugin.domain.cooldown;


import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;


public class InteractionSpeedHandler implements Listener {
    private static final long INTERACTION_THROTTLE_INTERVAL = 150L;
    private static final long BLOCK_INTERACTION_THROTTLE_INTERVAL = 400L;
    private static final long MOB_INTERACTION_THROTTLE_INTERVAL = 1000L;

    private final CooldownHandler interactionSpeedHandler;
    private final CooldownHandler blockInteractionSpeedHandler;

    public InteractionSpeedHandler() {
        this.interactionSpeedHandler = new CooldownHandler();
        this.blockInteractionSpeedHandler = new CooldownHandler();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (interactionSpeedHandler.isCooledDown(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }

        interactionSpeedHandler.startCooldown(event.getPlayer(), INTERACTION_THROTTLE_INTERVAL);
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (interactionSpeedHandler.isCooledDown(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }

        interactionSpeedHandler.startCooldown(event.getPlayer(), INTERACTION_THROTTLE_INTERVAL);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event) {
        if (interactionSpeedHandler.isCooledDown(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }

        interactionSpeedHandler.startCooldown(event.getPlayer(), MOB_INTERACTION_THROTTLE_INTERVAL);

    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerBlockInteract(PlayerInteractEvent event) {
        if (event.useInteractedBlock() == Event.Result.DENY ||
                event.useItemInHand() == Event.Result.DENY) {
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_AIR) {
            return;
        }

        if (blockInteractionSpeedHandler.isCooledDown(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }

        blockInteractionSpeedHandler.startCooldown(event.getPlayer(), BLOCK_INTERACTION_THROTTLE_INTERVAL);
    }

}
