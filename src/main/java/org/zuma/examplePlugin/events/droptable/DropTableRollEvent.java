package org.zuma.examplePlugin.events.droptable;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.zuma.examplePlugin.domain.droptable.DropTable;
import org.zuma.examplePlugin.domain.item.ItemRecord;

public class DropTableRollEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final DropTable dropTable;

    @Nullable
    private final ItemRecord rollResult;

    public DropTableRollEvent(DropTable dropTable, @Nullable ItemRecord rollResult) {
        this.dropTable = dropTable;
        this.rollResult = rollResult;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public
    static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public DropTable getDropTable() {
        return dropTable;
    }

    @Nullable
    public ItemRecord getRollResult() {
        return rollResult;
    }
}
