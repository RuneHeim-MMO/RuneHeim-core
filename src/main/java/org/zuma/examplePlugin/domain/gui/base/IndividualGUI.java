package org.zuma.examplePlugin.domain.gui.base;

import java.util.UUID;

public abstract class IndividualGUI extends AbstractGUI {
    private UUID owner;

    public IndividualGUI(String title, int id, int rows) {
        super(title, id, rows, false);
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID uuid) {
        this.owner = uuid;
    }
}
