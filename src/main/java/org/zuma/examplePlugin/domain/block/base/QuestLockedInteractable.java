package org.zuma.examplePlugin.domain.block.base;

import eu.decentsoftware.holograms.api.DHAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Door;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public abstract class QuestLockedInteractable extends AbstractInteractableBlock {
    private final String lockedMessage;
    private final List<String> requiredQuestIds, requiredStartedQuestIds;

    public QuestLockedInteractable(AbstractInteractableBlockMeta meta, String lockedMessage, @Nullable List<String> requiredQuestIds, @Nullable List<String> requiredStartedQuestIds) {
        super(meta, null, null);
        this.lockedMessage = lockedMessage;
        this.requiredQuestIds = requiredQuestIds != null ? requiredQuestIds : new ArrayList<>();
        this.requiredStartedQuestIds = requiredStartedQuestIds != null ? requiredStartedQuestIds : new ArrayList<>();
    }

    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItemType itemInHand) {
        event.setCancelled(true);
        boolean allowed = new HashSet<>(player.getCompletedQuestIds()).containsAll(requiredQuestIds) && player.getActiveQuestSteps().keySet().containsAll(requiredStartedQuestIds);

        if (!allowed) {
            HudService.sendFailMessage(player, Component.text(lockedMessage));
            return;
        }

        onInteractSuccess(player, action, event, itemInHand);
    }

    public abstract void onInteractSuccess(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItemType itemInHand);
}
