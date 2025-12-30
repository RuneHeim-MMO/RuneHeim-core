package org.zuma.examplePlugin.domain.block.impl.trigger;

import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Switch;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.domain.block.base.AbstractInteractableBlock;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ButtonTrigger extends AbstractInteractableBlock implements Trigger {
    private final String groupId, triggeredEntityId, title;
    private final int triggeredDurationSeconds;
    private boolean isTriggered;

    public ButtonTrigger(AbstractInteractableBlockMeta meta, String groupId, String triggeredEntityId, int triggeredDurationSeconds, @Nullable String title) {
        super(meta, null, null);
        this.groupId = groupId;
        this.triggeredEntityId = triggeredEntityId;
        this.triggeredDurationSeconds = triggeredDurationSeconds;
        this.isTriggered = false;
        this.title = title;
    }

    public String getGroupId() {
        return groupId;
    }

    public boolean isTriggered() {
        return isTriggered;
    }

    @Override
    public void onPlayerInteractEvent(RunePlayer player, Action action, PlayerInteractEvent event, AbstractItemType itemInHand) {
        event.setCancelled(true);

        if (isTriggered) {
            return;
        }

        Block block = getLocation().getBlock();
        this.isTriggered = true;
        RuneQuestPlugin.getBlockManager().getTriggeredBlocksHandler().onTriggerTriggered(groupId, triggeredEntityId);

        BlockData blockData = block.getBlockData();
        if (blockData instanceof Switch buttonData) {
            buttonData.setPowered(true);
            block.setBlockData(buttonData);
        }

        SoundType.BUTTON_PRESS.play(getLocation());
    }

    public void startStateResetCountdown() {
        Bukkit.getScheduler().runTaskLater(RuneQuestPlugin.getInstance(), () -> {
            Block block = getLocation().getBlock();
            this.isTriggered = false;
            RuneQuestPlugin.getBlockManager().getTriggeredBlocksHandler().onTriggerTriggered(groupId, triggeredEntityId);

            BlockData blockData = block.getBlockData();
            if (blockData instanceof Switch buttonData) {
                buttonData.setPowered(false);
                block.setBlockData(buttonData);
            }
        }, triggeredDurationSeconds * 20L);

        SoundType.BUTTON_RELEASE.play(getLocation());

    }

    private void initializeHologram() {
        if (title == null || title.isEmpty()) {
            return;
        }

        List<String> hologramContent = new ArrayList<>(List.of(title));
        setHologram(DHAPI.createHologram(UUID.randomUUID().toString(), getHologramLocation().clone().subtract(0, 1, 0), hologramContent));
    }


    @Override
    public void spawn(Material material) {
        super.spawn(Material.STONE_BUTTON);
    }


    public void initializeBlock() {
        initializeHologram();
        this.spawn(Material.STONE_BUTTON);
    }
}
