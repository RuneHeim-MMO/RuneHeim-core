package org.zuma.examplePlugin.domain.item.impl;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.RayTraceResult;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EmptyContainer extends AbstractItemType {

    public record EmptyContainerConfig(InputType type, int resultItemId) {
    }

    public enum InputType {
        WATER("Water"), LAVA("Lava"), FLOUR("Flour");

        private final String label;

        InputType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return this.label;
        }

    }

    private final Map<InputType, Integer> resultMap = new HashMap<>();

    public EmptyContainer(AbstractItemMeta meta, List<EmptyContainerConfig> config) {
        super(meta);
        super.setTypeLore("Container", Icon.BUCKET.get());

        config.forEach(entry -> resultMap.put(entry.type, entry.resultItemId));
    }

    public Set<InputType> getSupportedStoredItems() {
        return resultMap.keySet();
    }

    private void replaceItemInHandWithFilledVariant(RunePlayer player, InputType type) {
        Integer matchingFilledItem = resultMap.get(type);

        if (matchingFilledItem == null) {
            return;
        }

        AbstractItemType filled = RuneQuestPlugin.getItemRegistry().getItem(matchingFilledItem);

        if (filled == null) {
            RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Item of container type: " + super.displayName + " contains invalid filled item id (" + matchingFilledItem + ")! No such item exists.");
            player.asBukkitPlayer().sendMessage("This shouldn't happen. Send bug report please.");
            return;
        }

        PlayerUtils.subtractOneFromMainHand(player);
        ItemStack newItem = filled.createItemStackOfType();
        player.giveItemOrDrop(newItem);
    }


    @Override
    public void onPlayerInteract(PlayerInteractEvent event, RunePlayer player, StateManager itemStateManager) {
        // We care about both air and block right-clicks
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        // Perform ray trace up to 5 blocks and include fluids
        RayTraceResult result = player.asBukkitPlayer().rayTraceBlocks(5.0, FluidCollisionMode.ALWAYS);
        if (result == null || result.getHitBlock() == null) {
            return;
        }

        Block targetBlock = result.getHitBlock();
        Material blockType = targetBlock.getType();

        switch (blockType) {
            case WATER -> {
                replaceItemInHandWithFilledVariant(player, InputType.WATER);
            }
            case LAVA -> {
                replaceItemInHandWithFilledVariant(player, InputType.LAVA);
            }
        }


    }
}
