package org.zuma.examplePlugin.domain.gui.impl.individual.admin;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.RayTraceResult;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.meta.VersionedJson;
import org.zuma.examplePlugin.domain.block.BlockLoader;
import org.zuma.examplePlugin.domain.block.constants.BlockType;
import org.zuma.examplePlugin.domain.block.constants.ResourceNodeTypeId;
import org.zuma.examplePlugin.domain.block.meta.AbstractInteractableBlockMeta;
import org.zuma.examplePlugin.domain.block.meta.HologramSide;
import org.zuma.examplePlugin.domain.block.meta.ResourceNodeType;
import org.zuma.examplePlugin.domain.block.meta.v1.ResourceNodeMeta;
import org.zuma.examplePlugin.domain.block.persistence.BlockEntity;
import org.zuma.examplePlugin.domain.gui.base.IndividualGUI;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.HashMap;
import java.util.List;


public class NodeSelectorInventory extends IndividualGUI {
    private final BlockLoader loader;

    private static final HashMap<Integer, SchematicsRecord> SCHEMATIC_MAP = new HashMap<>() {{
        put(ResourceNodeTypeId.TREE.asInt(), new SchematicsRecord("tree", "tree_depleted"));
        put(ResourceNodeTypeId.BIRCH_TREE.asInt(), new SchematicsRecord("birch_tree", "birch_tree_depleted"));
        put(ResourceNodeTypeId.MAPLE_TREE.asInt(), new SchematicsRecord("maple_tree", "maple_tree_depleted"));
        put(ResourceNodeTypeId.YEW_TREE.asInt(), new SchematicsRecord("yew_tree", "yew_tree_depleted"));
        put(ResourceNodeTypeId.MAHOGANY_TREE.asInt(), new SchematicsRecord("mahogany_tree", "mahogany_tree_depleted"));
        put(ResourceNodeTypeId.MAGIC_TREE.asInt(), new SchematicsRecord("magic_tree", "magic_tree_depleted"));
    }};


    public record SchematicsRecord(String intact, String depleted) {
    }


    public NodeSelectorInventory(String title, int id, BlockLoader loader) {
        super(title, id, 6);
        this.loader = loader;
    }

    @Override
    public void openInventory(RunePlayer player) {
        if (!player.getPlayer().isOp()) {
            return;
        }

        Inventory inventory = getBaseInventory();
        List<ResourceNodeType> types = loader.getResourceNodeTypes().values().stream().toList();

        for (int i = 0; i < types.size(); i++) {

            if (i >= super.getSlots() - 1) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, title + " inventory is overflowing.");
                continue;
            }

            ResourceNodeType type = types.get(i);
            AbstractItem dropItem = RuneQuestPlugin.getItemRegistry().getItem(type.getDrop().getItemId());

            if (dropItem == null) {
                return;
            }

            ItemStack item = new ItemStack(dropItem.asItemStack());
            ItemMeta meta = item.getItemMeta();
            meta.displayName(Component.text(type.getLabel()));
            item.setItemMeta(meta);
            super.assignMenuValue(item, i);

            inventory.setItem(i, item);
        }

        ItemStack undo = new ItemStack(Material.BARRIER);
        ItemMeta meta = undo.getItemMeta();
        meta.displayName(Component.text("Undo", Colors.ERROR.asColor()));
        undo.setItemMeta(meta);
        super.assignMenuValue(undo, -1);
        inventory.setItem(super.getSlots() - 1, undo);

        player.getPlayer().openInventory(inventory);
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event, int slot, RunePlayer player) {
        event.setCancelled(true);

        ItemStack currentItem = event.getCurrentItem();

        if (currentItem == null) {
            return;
        }

        Integer menuValue = getMenuValue(currentItem);

        if (menuValue == null) {
            return;
        }

        if (menuValue == -1) { // Undo
            loader.undoLastInsertion();
            return;
        }

        RayTraceResult result = player.getPlayer().rayTraceBlocks(20);

        if (result == null || result.getHitBlock() == null) {
            return;
        }

        List<ResourceNodeType> types = loader.getResourceNodeTypes().values().stream().toList();
        ResourceNodeType type = types.get(menuValue);
        SchematicsRecord schematics = SCHEMATIC_MAP.get(type.getId());

        loader.insertNewNode(new BlockEntity(
                new AbstractInteractableBlockMeta(0, BlockType.ResourceNode, result.getHitBlock().getLocation(), HologramSide.TOP),
                new VersionedJson(new Gson().toJson(new ResourceNodeMeta(type.getId(), schematics.intact(), schematics.depleted())), 1)));

    }
}
