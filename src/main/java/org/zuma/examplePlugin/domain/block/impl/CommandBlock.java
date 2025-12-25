package org.zuma.examplePlugin.domain.block.impl;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandBlock extends AbstractInteractableBlock {
    private final List<String> hologramContent;
    private final List<String> commands;

    public CommandBlock(AbstractInteractableBlockMeta meta) {
        super(meta, null, null);
        this.hologramContent = new ArrayList<>();
        this.commands = new ArrayList<>();
    }

    private void initializeHologram() {
        setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), getHologramLocation(), hologramContent));
    }

    public void initializeBlock(List<String> hologramContent, List<String> commands) {
        this.hologramContent.addAll(hologramContent);
        this.commands.addAll(commands);
        initializeHologram();
        super.spawn(this.getType().getMaterial());
    }

    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItemType itemInHand) {
        event.setCancelled(true);

        for (String command : commands) {
            player.asBukkitPlayer().performCommand(command.replace("{player}", player.asBukkitPlayer().getName()));
        }

    }
}
