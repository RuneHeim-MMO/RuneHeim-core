package org.zuma.examplePlugin.domain.combat.ranged;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;

import java.util.*;

public class BowDrawHelper implements Listener {
    private final Map<UUID, Long> drawStartTimes = new HashMap<>();
    private final Set<UUID> plingPlayed = new HashSet<>();

    public BowDrawHelper() {
        Bukkit.getScheduler().runTaskTimer(RuneQuestPlugin.getInstance(), this::checkPlayersDrawing, 5L, 5L);
    }


    // When player starts drawing bow
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getItem().getType() != Material.BOW) return;
        System.out.println("1");
        if (!event.getAction().toString().contains("RIGHT_CLICK")) return;
        System.out.println("12");
        Player player = event.getPlayer();

        // Ammo check (has arrows or infinity)
        if (!hasAmmo(player, event.getItem())) return;
        System.out.println("13");
        // Record draw start time (System.nanoTime for precision)
        drawStartTimes.put(player.getUniqueId(), System.nanoTime());
        plingPlayed.remove(player.getUniqueId());
        System.out.println("41");
    }

    // When player shoots or cancels draw, clean up
    @EventHandler
    public void onShootBow(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        drawStartTimes.remove(player.getUniqueId());
        plingPlayed.remove(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerStopDrawing(PlayerAnimationEvent event) {
        // Player released right click but no arrow shot? Clean up
        drawStartTimes.remove(event.getPlayer().getUniqueId());
        plingPlayed.remove(event.getPlayer().getUniqueId());
        System.out.println("15");
    }

    private boolean hasAmmo(Player player, ItemStack bow) {
        return player.getInventory().contains(Material.ARROW); // Todo: update
    }

    private void checkPlayersDrawing() {
        long now = System.nanoTime();

        Iterator<Map.Entry<UUID, Long>> it = drawStartTimes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<UUID, Long> entry = it.next();
            UUID uuid = entry.getKey();
            long startTime = entry.getValue();
            Player player = Bukkit.getPlayer(uuid);

            if (player == null || !player.isOnline()) {
                it.remove();
                plingPlayed.remove(uuid);
                continue;
            }

            double elapsedSeconds = (now - startTime) / 1_000_000_000.0;

            // Bow full draw takes 3 seconds for power 3.0 max
            // So scale elapsedSeconds linearly from 0 to 3 seconds -> 0 to 3 power
            double power = Math.min(3.0, elapsedSeconds * 3);

            System.out.println("Player " + player.getName() + " power: " + power); // DEBUG

            if (power >= 2.0 && !plingPlayed.contains(uuid)) {
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                plingPlayed.add(uuid);
                System.out.println("Pling sound played for " + player.getName()); // DEBUG
            }

            if (power >= 3.0) {
                it.remove();
                plingPlayed.remove(uuid);
            }
        }
    }

}
