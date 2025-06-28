package org.zuma.examplePlugin.domain.modifier;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.zuma.examplePlugin.core.utils.ModifierUtils;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.base.AbstractType;
import org.zuma.examplePlugin.domain.item.base.EquipableType;
import org.zuma.examplePlugin.domain.item.base.SocketedEquipableType;
import org.zuma.examplePlugin.domain.item.state.states.GearGemSocketsState;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.modifier.GearModifiersRecalculatedEvent;

import java.util.*;

public class GearModifierHandler {
    private final ItemRegistry itemRegistry;

    public GearModifierHandler(ItemRegistry registry) {
        this.itemRegistry = registry;
    }

    public void recalculatePlayerModifiers(RunePlayer player, ItemStack itemInHand) {
        List<Modifier> gearModifiers = getGearModifiers(player, itemInHand);
        List<Modifier> combined = ModifierUtils.combineModifiersByType(gearModifiers);

        player.setModifiers(combined);
        Bukkit.getServer().getPluginManager().callEvent(new GearModifiersRecalculatedEvent(player));
    }

    private List<Modifier> getGearModifiers(RunePlayer player, ItemStack itemInHand) {
        PlayerInventory inventory = player.asBukkitPlayer().getInventory();
        List<Modifier> modifiers = new ArrayList<>();
        List<ItemStack> items = new ArrayList<>(Arrays.asList(inventory.getArmorContents()));

        if (itemInHand != null && itemInHand.getType() != Material.AIR) {
            AbstractType item = itemRegistry.getItem(itemInHand);

            if (item instanceof EquipableType gearItem && gearItem.getApplyModifiersWhenInHand()) {
                items.add(itemInHand);
            }
        }

        items.stream()
                .filter(Objects::nonNull)
                // map each ItemStack to a pair (ItemStack, AbstractType)
                .map(itemStack -> Map.entry(itemStack, itemRegistry.getItem(itemStack)))
                // filter out entries where AbstractType is not EquipableType or requirements not met
                .filter(entry -> {
                    AbstractType abstractType = entry.getValue();
                    if (abstractType == null) {
                        return false;
                    }

                    if (!(abstractType instanceof EquipableType gearItem)) {
                        return false;
                    }
                    return player.meetsRequirements(gearItem);
                })
                .forEach(entry -> {
                    ItemStack itemStack = entry.getKey();
                    EquipableType gearItem = (EquipableType) entry.getValue();

                    modifiers.addAll(gearItem.getModifiers());

                    if (gearItem instanceof SocketedEquipableType withSockets) {
                        GearGemSocketsState state = withSockets.getState(itemStack);

                        if (state != null) {
                            modifiers.addAll(
                                    state.getInsertedGems()
                                            .stream()
                                            .flatMap(gem -> gem.getModifiers().stream())
                                            .toList());
                        }
                    }
                });

        return modifiers;
    }
}
