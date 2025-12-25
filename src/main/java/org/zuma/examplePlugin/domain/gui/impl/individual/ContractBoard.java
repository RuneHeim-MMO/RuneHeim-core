package org.zuma.examplePlugin.domain.gui.impl.individual;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ContractBoard extends IndividualGUI {
    private static final Set<Integer> IGNORED_SLOTS = new HashSet<>(Arrays.asList(0, 1, 9, 10));

    public ContractBoard(String title, int id) {
        super(title, id, 6);

    }


    @Override
    public boolean attemptOpenInventory(RunePlayer player, Runnable onFail) {
        player.asBukkitPlayer().openInventory(getBaseInventory());
        SoundType.UI_SPOOKY_INVENTORY_OPEN.play(player, false);
        return true;
    }


    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);
    }

}
