package org.zuma.examplePlugin.events.combat;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.core.interfaces.CombatEntity;
import org.zuma.examplePlugin.domain.combat.Hit;
import org.zuma.examplePlugin.domain.combat.constants.AttackType;

public class RuneEntityDamageEntityEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final CombatEntity attacker;
    private final CombatEntity defender;
    private final Hit hit;
    private final AttackType type;
    private boolean cancelled;

    public RuneEntityDamageEntityEvent(CombatEntity attacker, CombatEntity defender, Hit hit, AttackType type) {
        this.attacker = attacker;
        this.defender = defender;
        this.hit = hit;
        this.type = type;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
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

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
