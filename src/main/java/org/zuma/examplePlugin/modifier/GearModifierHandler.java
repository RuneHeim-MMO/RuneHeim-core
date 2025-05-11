package org.zuma.examplePlugin.modifier;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.zuma.examplePlugin.event.GearModifiersRecalculatedEvent;
import org.zuma.examplePlugin.item.ItemRegistry;
import org.zuma.examplePlugin.item.base.GearItem;
import org.zuma.examplePlugin.player.RunePlayer;
import org.zuma.examplePlugin.utils.ModifierUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GearModifierHandler {
    private final ItemRegistry itemRegistry;

    public GearModifierHandler(ItemRegistry registry) {
        this.itemRegistry = registry;
    }

    public void recalculatePlayerModifiers(RunePlayer player, ItemStack itemInHand) {
        List<Modifier> gearModifiers = getGearModifiers(player, itemInHand);
        List<Modifier> combined = ModifierUtils.combineModifiersByType(gearModifiers);

        player.setGearModifiers(combined);
        Bukkit.getServer().getPluginManager().callEvent(new GearModifiersRecalculatedEvent(player));
    }

    private List<Modifier> getGearModifiers(RunePlayer player, ItemStack itemInHand) {
        PlayerInventory inventory = player.getPlayer().getInventory();
        List<Modifier> modifiers = new ArrayList<>();

        List<ItemStack> items = new ArrayList<>(Arrays.asList(inventory.getArmorContents()));
        items.add(itemInHand);

        items.stream()
                .filter(item -> item != null)
                .map(itemRegistry::getItem)
                .filter(item -> item instanceof GearItem)
                .map(item -> (GearItem) item)
                .forEach(gearItem -> modifiers.addAll(gearItem.modifiers));


        return modifiers;
    }
}
