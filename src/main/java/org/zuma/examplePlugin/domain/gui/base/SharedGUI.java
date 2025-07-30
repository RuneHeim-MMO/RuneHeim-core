package org.zuma.examplePlugin.domain.gui.base;

import org.bukkit.inventory.Inventory;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public abstract class SharedGUI extends AbstractGUI {
    protected Inventory inventory = null;

    public SharedGUI(String title, int id, int rows) {
        super(title, id, rows, true);
        inventory = getBaseInventory();
        this.initializeInventory(inventory);
    }

    public abstract void initializeInventory(Inventory inventory);

    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        if (inventory == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, player.asBukkitPlayer().getName() + " attempted to open shared inventory id " + super.id + ". Cannot open inventory because it want initialized.");
        }

        player.asBukkitPlayer().openInventory(inventory);
        return true;
    }


}
