package org.zuma.examplePlugin.domain.block.impl;

import eu.decentsoftware.holograms.api.DHAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.interfaces.POIMarkerable;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.map.MarkerType;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.actions.ActionParams;
import org.zuma.examplePlugin.domain.player.actions.ProgressBarAction;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
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
