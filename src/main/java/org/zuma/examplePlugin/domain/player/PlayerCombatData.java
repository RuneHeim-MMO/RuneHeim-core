package org.zuma.examplePlugin.domain.player;

import org.bukkit.Bukkit;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;
import org.zuma.examplePlugin.events.player.RunePlayerHealthChangedEvent;

public class PlayerCombatData {
    private final RunePlayer player;
    private int combatLvl, health;

    public PlayerCombatData(RunePlayer player, int combatLvl, int health) {
        this.player = player;
        this.combatLvl = combatLvl;
        this.health = health;
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
        RunePlayerHealthChangedEvent event = new RunePlayerHealthChangedEvent(player, health, cause);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        this.health = health;
    }

    public void takeHit(Hit hit) {
        this.health -= Math.min(this.health, hit.damage());


        if (this.health <= 0) {
            player.asBukkitPlayer().sendMessage("Welp, you died!");
            setHealth(player.getSkills().get(SkillType.HITPOINTS).getLevel(), RunePlayerHealthChangedEvent.Cause.COMBAT);
        }

        HudService.updateVanillaGUI(player);
    }
}
