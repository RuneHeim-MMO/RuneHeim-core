package org.zuma.examplePlugin.domain.combat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.domain.combat.constants.AttackType;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.combat.RuneEntityDamageEntityEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HitHandler {
    private static final List<UUID> playersInDebugMode = new ArrayList<>();

    private void handleHitSound(CombatEntity entity, AttackType type, Hit hit) {
        SoundType sound = null;

        switch (hit.hitType()) {
            case CRIT -> {
                switch (type) {
                    case MELEE -> sound = SoundType.HIT_MELEE_CRITICAL;
                    case MAGIC -> sound = SoundType.HIT_MAGIC_CRITICAL;
                    case RANGED -> sound = SoundType.HIT_RANGED_CRITICAL;
                }
            }
            case REGULAR -> {
                switch (type) {
                    case MELEE -> sound = SoundType.HIT_MELEE_REGULAR;
                    case MAGIC -> sound = SoundType.HIT_MAGIC_REGULAR;
                    case RANGED -> sound = SoundType.HIT_RANGED_REGULAR;
                }
            }
            case MISS -> {
                switch (type) {
                    case MELEE -> sound = SoundType.HIT_MELEE_MISS;
                    case MAGIC -> sound = SoundType.HIT_MAGIC_MISS;
                    case RANGED -> sound = SoundType.HIT_RANGED_MISS;
                }
            }
        }

        if (sound != null) {
            sound.play(entity.getEntity().getLocation());
        }
    }

    @Nullable
    public Hit handleMeleeHit(CombatEntity attacker, CombatEntity defender) {
        AttackType type = AttackType.MELEE;
        Hit hit = HitCalculator.getNewHit(attacker, defender, type);

        RuneEntityDamageEntityEvent event = new RuneEntityDamageEntityEvent(attacker, defender, hit, type);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return null;
        }

        defender.registerHit(hit);
        handleHitSound(defender, type, hit);
        HudService.showDamageBubble(defender, hit, type);

        showHitDebugInfo(attacker, defender, hit);
        return hit;
    }

    @Nullable
    public Hit handleRangedHit(CombatEntity attacker, CombatEntity defender) {
        AttackType type = AttackType.RANGED;
        Hit hit = HitCalculator.getNewHit(attacker, defender, type);

        RuneEntityDamageEntityEvent newEvent = new RuneEntityDamageEntityEvent(attacker, defender, hit, type);
        Bukkit.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            return null;
        }

        defender.registerHit(hit);
        handleHitSound(defender, type, hit);
        HudService.showDamageBubble(defender, hit, type);

        showHitDebugInfo(attacker, defender, hit);
        return hit;
    }

    @Nullable
    public Hit handleMagicHit(CombatEntity attacker, CombatEntity defender) {
        AttackType type = AttackType.MAGIC;
        Hit hit = HitCalculator.getNewHit(attacker, defender, type);

        RuneEntityDamageEntityEvent newEvent = new RuneEntityDamageEntityEvent(attacker, defender, hit, type);
        Bukkit.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            return null;
        }

        defender.registerHit(hit);
        handleHitSound(defender, type, hit);
        HudService.showDamageBubble(defender, hit, type);

        showHitDebugInfo(attacker, defender, hit);
        return hit;
    }

    private void showHitDebugInfo(CombatEntity attacker, CombatEntity defender, Hit hit) {
        Entity attackerEntity = attacker.getEntity();
        Entity defenderEntity = defender.getEntity();

        if (attackerEntity == null || defenderEntity == null) {
            return;
        }


        boolean isAttackerInDebug = false;
        boolean isDefenderInDebug = false;

        if (playersInDebugMode.contains(attackerEntity.getUniqueId())) {
            isAttackerInDebug = true;
        }


        if (playersInDebugMode.contains(defenderEntity.getUniqueId())) {
            isDefenderInDebug = true;
        }

        if (!isAttackerInDebug && !isDefenderInDebug) {
            return;
        }

        if (isAttackerInDebug && attackerEntity instanceof Player attackerPlayer) {
            Component message = Component.text("⚔ ", NamedTextColor.GOLD)
                    .append(Component.text("Hit: ", NamedTextColor.YELLOW))
                    .append(Component.text(hit.damage(), NamedTextColor.RED))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("Max Hit: ", NamedTextColor.YELLOW))
                    .append(Component.text(hit.maxDamage(), NamedTextColor.RED))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("Hit Chance: ", NamedTextColor.YELLOW))
                    .append(Component.text(String.format("%.3f", hit.chance()) + " %", NamedTextColor.GREEN))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("Type: ", NamedTextColor.YELLOW))
                    .append(Component.text(hit.type().toString(), NamedTextColor.AQUA));

            attackerPlayer.sendMessage(message);
        }

        if (isDefenderInDebug && defenderEntity instanceof Player defenderPlayer) {
            Component message = Component.text("🛡 ", NamedTextColor.DARK_RED)
                    .append(Component.text("Hit: ", NamedTextColor.YELLOW))
                    .append(Component.text(hit.damage(), NamedTextColor.RED))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("Max Hit: ", NamedTextColor.YELLOW))
                    .append(Component.text(hit.maxDamage(), NamedTextColor.RED))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("Hit Chance: ", NamedTextColor.YELLOW))
                    .append(Component.text(String.format("%.2f", hit.chance()) + " %", NamedTextColor.GREEN))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("Type: ", NamedTextColor.YELLOW))
                    .append(Component.text(hit.type().toString(), NamedTextColor.AQUA));

            defenderPlayer.sendMessage(message);
        }


    }

    public void toggleCombatDebug(RunePlayer player) {
        UUID uuid = player.getUUID();

        if (playersInDebugMode.contains(uuid)) {
            playersInDebugMode.remove(uuid);
            player.asBukkitPlayer().sendMessage(Component.text("Combat debug mode: OFF", NamedTextColor.RED));
            return;
        }

        player.asBukkitPlayer().sendMessage(Component.text("Combat debug mode: ON", NamedTextColor.GREEN));
        playersInDebugMode.add(uuid);

    }
}
