package org.zuma.examplePlugin.domain.player;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.player.RunePlayerHealthChangedEvent;

public class PlayerCombatData {
    private final static long OUT_OF_COMBAT_INTERVAL_SECONDS = 30;

    private final RunePlayer player;
    private int combatLvl, health;
    private Long damagedAt;
    private float specialEnergy;

    public PlayerCombatData(RunePlayer player, int health, int combatLvl) {
        this.player = player;
        this.combatLvl = combatLvl;
        this.health = health;
        this.damagedAt = System.currentTimeMillis();
        this.specialEnergy = 1.0F;
    }

    public int getCombatLvl() {
        return combatLvl;
    }

    public void setCombatLvl(int combatLvl) {
        this.combatLvl = combatLvl;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health, RunePlayerHealthChangedEvent.Cause cause) {
        if (cause != RunePlayerHealthChangedEvent.Cause.INITIALIZATION) {
            RunePlayerHealthChangedEvent event = new RunePlayerHealthChangedEvent(player, health, cause);
            Bukkit.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return;
            }

        }

        this.health = health;
    }

    public boolean isInCombat() {
        return System.currentTimeMillis() < this.damagedAt + (OUT_OF_COMBAT_INTERVAL_SECONDS * 1000);
    }

    public void registerHit(Hit hit) {
        if (this.health > player.getMaxHealth()) {
            this.health = player.getMaxHealth();
        }

        this.health -= Math.min(this.health, hit.damage());
        this.damagedAt = System.currentTimeMillis();

        if (this.health <= 0) {
            player.onDeath(hit);
            this.damagedAt = 0L;
            setHealth(player.getSkills().get(SkillType.HITPOINTS).getBaseLevel(), RunePlayerHealthChangedEvent.Cause.COMBAT);
        }

        HudService.updateVanillaGUI(player);
    }

    public void performSpecialAttack() {
        this.specialEnergy = Math.max(0, this.specialEnergy - 0.25F);
    }

    public boolean canPerformSpecialAttack(){
        return this.specialEnergy > 0.25F;
    }

    public void recoverSpecialEnergy() {
        this.specialEnergy = Math.min(1, this.specialEnergy + 0.1F);
    }

    public float getSpecialEnergy() {
        return this.specialEnergy;
    }
}
