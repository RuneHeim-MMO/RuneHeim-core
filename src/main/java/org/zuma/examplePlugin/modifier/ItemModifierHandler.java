package org.zuma.examplePlugin.modifier;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.zuma.examplePlugin.item.ItemRegistry;
import org.zuma.examplePlugin.item.base.ItemWithModifiers;
import org.zuma.examplePlugin.player.RunePlayer;
import org.zuma.examplePlugin.utils.ModifierUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemModifierHandler {
    private final ItemRegistry itemRegistry;

    public ItemModifierHandler(ItemRegistry registry) {
        this.itemRegistry = registry;
    }

    public void recalculatePlayerModifiers(RunePlayer player) {
        List<Modifier> gearModifiers = getGearModifiers(player);
        List<Modifier> combined = ModifierUtils.combineModifiersByType(gearModifiers);

        player.setGearModifiers(combined);
    }

    private List<Modifier> getGearModifiers(RunePlayer player) {
        PlayerInventory inventory = player.getPlayer().getInventory();
        List<Modifier> modifiers = new ArrayList<>();

        List<ItemStack> items = new ArrayList<>(Arrays.asList(inventory.getArmorContents()));
        items.add(inventory.getItemInMainHand());

        items.stream()
                .map(itemRegistry::getItem)
                .filter(item -> item instanceof ItemWithModifiers)
                .map(item -> (ItemWithModifiers) item)
                .forEach(itemWithModifiers -> modifiers.addAll(itemWithModifiers.modifiers));


        return modifiers;
    }
}
