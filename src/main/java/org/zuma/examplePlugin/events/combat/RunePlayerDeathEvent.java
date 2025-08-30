package org.zuma.examplePlugin.events.combat;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.combat.constants.AttackType;

public class RunePlayerDeathEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final CombatEntity attacker;
    private final CombatEntity defender;
    private final Hit hit;
    private final AttackType type;

    public RunePlayerDeathEvent(CombatEntity attacker, CombatEntity defender, Hit hit, AttackType type) {
        this.attacker = attacker;
        this.defender = defender;
        this.hit = hit;
        this.type = type;
    }

    public CombatEntity getAttacker() {
        return attacker;
    }

    public CombatEntity getDefender() {
        return defender;
    }

    public Hit getHit() {
        return hit;
    }

    public AttackType getAttackType() {
        return type;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
