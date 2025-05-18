package org.zuma.examplePlugin.domain.modifier;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.zuma.examplePlugin.core.utils.ModifierUtils;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.base.EquipableItem;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.modifier.GearModifiersRecalculatedEvent;

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
                .filter(item -> {
                    if (!(item instanceof EquipableItem gearItem)) {
                        return false;
                    }
                    return player.meetsRequirements(gearItem);
                })
                .map(item -> (EquipableItem) item)
                .forEach(gearItem -> modifiers.addAll(gearItem.getModifiers()));


        return modifiers;
    }
}
