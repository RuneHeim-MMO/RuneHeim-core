package org.zuma.examplePlugin.domain.combat;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.domain.combat.constants.AttackType;
import org.zuma.examplePlugin.domain.effect.EffectHandler;
import org.zuma.examplePlugin.domain.effect.effects.SpecialAttackSmokeEffect;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.mob.base.AbstractMob;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.combat.RuneEntityDamageEntityEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HitHandler {
    private static final List<UUID> playersInDebugMode = new ArrayList<>();

    private void handleHitSound(CombatEntity entity, Hit hit) {
        SoundType sound = null;

        switch (hit.getHitType()) {
            case CRIT -> {
                switch (hit.getAttackType()) {
                    case MELEE -> sound = SoundType.HIT_MELEE_CRITICAL;
                    case MAGIC -> sound = SoundType.HIT_MAGIC_CRITICAL;
                    case RANGED -> sound = SoundType.HIT_RANGED_CRITICAL;
                }
            }
            case REGULAR -> {
                switch (hit.getAttackType()) {
                    case MELEE -> sound = SoundType.HIT_MELEE_REGULAR;
                    case MAGIC -> sound = SoundType.HIT_MAGIC_REGULAR;
                    case RANGED -> sound = SoundType.HIT_RANGED_REGULAR;
                }
            }
            case MISS -> {
                switch (hit.getAttackType()) {
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

    private void handleSpecialHitSound(CombatEntity entity, Hit hit) {
        SoundType sound = null;

        switch (hit.getAttackType()) {
            case MELEE -> sound = SoundType.HIT_MELEE_SPECIAL;
            case RANGED -> sound = SoundType.HIT_RANGED_SPECIAL;
            case MAGIC -> sound = SoundType.HIT_MAGIC_SPECIAL;
        }

        if (sound != null) {
            sound.play(entity.getEntity().getLocation());
        }
    }

    public void applySplashHit(RunePlayer attacker, CombatEntity originalDefender, AttackType type, boolean isSpecialAttack, int coneDeg, double radius) {
        Player player = attacker.asBukkitPlayer();
        Vector direction = player.getLocation().getDirection(); // no normalization needed
        Location eyeLocation = player.getEyeLocation();

        double radiusWithBaseReach = radius + 3; // 3 blocks is base reach
        double radiusSquared = radiusWithBaseReach * radiusWithBaseReach;
        double coneDotThreshold = Math.cos(Math.toRadians(coneDeg / 2.0));

        List<Entity> nearbyEntities = player.getNearbyEntities(radiusWithBaseReach, radiusWithBaseReach, radiusWithBaseReach);
        for (Entity entity : nearbyEntities) {
            AbstractMob matchingMob = RuneQuestPlugin.getMobRegistry().getMob(entity);
            if (!(matchingMob instanceof CombatEntity combatMob)) continue;
            if (combatMob.equals(originalDefender)) continue; // skip original target

            Vector toEntity = entity.getLocation().toVector().subtract(eyeLocation.toVector());
            if (toEntity.lengthSquared() > radiusSquared) continue; // skip if outside radius

            double dot = toEntity.dot(direction);
            double magnitude = toEntity.length() * direction.length(); // only magnitude needed once
            if (dot / magnitude <= coneDotThreshold) continue; // skip if not in front

            Hit splashHit = HitCalculator.getNewHit(attacker, combatMob, type, isSpecialAttack, true);
            applyHit(attacker, combatMob, splashHit);
        }
    }

    @Nullable
    public Hit handleMeleeHit(CombatEntity attacker, CombatEntity defender) {
        AttackType type = AttackType.MELEE;
        boolean isSpecialAttack = attacker.isSpecialAttacking();
        Hit hit = HitCalculator.getNewHit(attacker, defender, type, isSpecialAttack, false);

        RuneEntityDamageEntityEvent event = new RuneEntityDamageEntityEvent(attacker, defender, hit, type);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return null;
        }

        if (attacker.getEntity() instanceof Player) {
            applySplashHit((RunePlayer) attacker, defender, type, isSpecialAttack, 60, (int) HitCalculator.getEntityModifierAmount(attacker, ModifierType.CLEAVE_DISTANCE));
        }

        applyHit(attacker, defender, hit);
        return hit;
    }

    @Nullable
    public Hit handleRangedHit(CombatEntity attacker, CombatEntity defender) {
        AttackType type = AttackType.RANGED;
        boolean isSpecialAttack = attacker.isSpecialAttacking();
        Hit hit = HitCalculator.getNewHit(attacker, defender, type, isSpecialAttack, false);

        RuneEntityDamageEntityEvent newEvent = new RuneEntityDamageEntityEvent(attacker, defender, hit, type);
        Bukkit.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            return null;
        }

        applyHit(attacker, defender, hit);
        return hit;
    }

    @Nullable
    public Hit handleMagicHit(CombatEntity attacker, CombatEntity defender) {
        AttackType type = AttackType.MAGIC;
        boolean isSpecialAttack = attacker.isSpecialAttacking();
        Hit hit = HitCalculator.getNewHit(attacker, defender, type, isSpecialAttack, false);

        RuneEntityDamageEntityEvent newEvent = new RuneEntityDamageEntityEvent(attacker, defender, hit, type);
        Bukkit.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            return null;
        }

        applyHit(attacker, defender, hit);
        return hit;
    }

    private void notifyMobDamageMechanics(CombatEntity attacker, CombatEntity defender, Hit hit) {
        AbstractMob mobAttacker = RuneQuestPlugin.getMobRegistry().getMob(attacker.getEntity());
        if (mobAttacker != null && defender.getEntity() instanceof Player) {
            RunePlayer defenderRunePlayer = RuneQuestPlugin.getPlayerManager().getRunePlayer((Player) defender.getEntity());
            if (defenderRunePlayer != null)
                mobAttacker.getMobMechanics().values().forEach(m -> m.onMobDamagePlayer(defenderRunePlayer, hit));
        }
    }

    private void applyHit(CombatEntity attacker, CombatEntity defender, Hit hit) {
        notifyMobDamageMechanics(attacker, defender, hit); // Can mutate hit
        boolean isSpecialAttack = attacker.isSpecialAttacking();

        if (isSpecialAttack) {
            attacker.onSpecialAttack(hit);

            if (!hit.isSplashHit()) {
                handleSpecialHitSound(attacker, hit);
                EffectHandler.playAt(defender.getEntity().getLocation(), new SpecialAttackSmokeEffect(hit.getAttackType()));
            }
        }

        defender.registerHit(hit);
        attacker.onHitLand(hit);
        handleHitSound(defender, hit);
        HudService.showDamageBubble(defender, hit);

        if (hit.getDamage() > 0 && defender.getEntity() instanceof LivingEntity livingDefender) {
            livingDefender.damage(0);
        }

        showHitDebugInfo(attacker, defender, hit);
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
                    .append(Component.text(hit.getDamage(), NamedTextColor.RED))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("Max Hit: ", NamedTextColor.YELLOW))
                    .append(Component.text(hit.getMaxDamage(), NamedTextColor.RED))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("Hit Chance: ", NamedTextColor.YELLOW))
                    .append(Component.text(String.format("%.3f", hit.getSuccessHitChance()) + " %", NamedTextColor.GREEN))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("Type: ", NamedTextColor.YELLOW))
                    .append(Component.text(hit.getAttackType().toString(), NamedTextColor.AQUA))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(hit.isSplashHit() ? Component.text(" Splash!", NamedTextColor.AQUA) : Component.empty());

            attackerPlayer.sendMessage(message);
        }

        if (isDefenderInDebug && defenderEntity instanceof Player defenderPlayer) {
            Component message = Component.text("🛡 ", NamedTextColor.DARK_RED)
                    .append(Component.text("Hit: ", NamedTextColor.YELLOW))
                    .append(Component.text(hit.getDamage(), NamedTextColor.RED))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("Max Hit: ", NamedTextColor.YELLOW))
                    .append(Component.text(hit.getMaxDamage(), NamedTextColor.RED))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("Hit Chance: ", NamedTextColor.YELLOW))
                    .append(Component.text(String.format("%.2f", hit.getSuccessHitChance()) + " %", NamedTextColor.GREEN))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(Component.text("Type: ", NamedTextColor.YELLOW))
                    .append(Component.text(hit.getAttackType().toString(), NamedTextColor.AQUA))
                    .append(Component.text(" | ", NamedTextColor.GRAY))
                    .append(hit.isSplashHit() ? Component.text(" Splash!", NamedTextColor.AQUA) : Component.empty());


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
