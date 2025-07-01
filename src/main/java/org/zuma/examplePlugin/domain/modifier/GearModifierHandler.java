package org.zuma.examplePlugin.domain.modifier;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.zuma.examplePlugin.core.utils.ModifierUtils;
import org.zuma.examplePlugin.domain.item.ItemRegistry;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.base.SocketedEquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.EquipableType;
import org.zuma.examplePlugin.domain.item.impl.Fairy;
import org.zuma.examplePlugin.domain.item.impl.FairyFruit;
import org.zuma.examplePlugin.domain.item.impl.Gem;
import org.zuma.examplePlugin.domain.item.state.states.FairyState;
import org.zuma.examplePlugin.domain.item.state.states.GearGemSocketsState;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.inventory.AdditionalGearSlotHandler;
import org.zuma.examplePlugin.events.modifier.GearModifiersRecalculatedEvent;

import java.util.*;

public class GearModifierHandler {
    private final ItemRegistry itemRegistry;
    private final AdditionalGearSlotHandler additionalGearSlotHandler;

    public GearModifierHandler(ItemRegistry registry, AdditionalGearSlotHandler additionalGearSlotHandler) {
        this.itemRegistry = registry;
        this.additionalGearSlotHandler = additionalGearSlotHandler;
    }

    public void recalculatePlayerModifiers(RunePlayer player, ItemStack itemInHand) {
        Map.Entry<List<Modifier>, List<SkillModifier>> gearModifiers = getGearModifiers(player, itemInHand);

        List<Modifier> combined = ModifierUtils.combineModifiersByType(gearModifiers.getKey());
        List<SkillModifier> combinedSkillModifiers = ModifierUtils.combineSkillModifiersByType(gearModifiers.getValue());

        player.setModifiers(combined);
        player.setSkillModifiers(combinedSkillModifiers);

        Bukkit.getServer().getPluginManager().callEvent(new GearModifiersRecalculatedEvent(player));
    }

    private Map.Entry<List<Modifier>, List<SkillModifier>> getGearModifiers(RunePlayer player, ItemStack itemInHand) {
        PlayerInventory inventory = player.asBukkitPlayer().getInventory();
        List<Modifier> modifiers = new ArrayList<>();
        List<SkillModifier> skillModifiers = new ArrayList<>();

        List<ItemStack> items = new ArrayList<>(Arrays.asList(inventory.getArmorContents()));
        items.addAll(additionalGearSlotHandler.getSlotItems(player));


        if (itemRegistry.getItem(itemInHand) instanceof EquipableItemType gearItem) {
            if (gearItem.getEquipableType().equals(EquipableType.HANDHELD)) items.add(itemInHand);
        }


        items.stream()
                .filter(Objects::nonNull)
                .map(itemStack -> Map.entry(itemStack, itemRegistry.getItem(itemStack)))
                .filter(entry -> {
                    AbstractItemType abstractItemType = entry.getValue();

                    if (abstractItemType == null) {
                        return false;
                    }

                    if (!(abstractItemType instanceof EquipableItemType gearItem)) {
                        return false;
                    }

                    return player.meetsRequirements(gearItem);
                })
                .forEach(entry -> {
                    ItemStack itemStack = entry.getKey();
                    EquipableItemType gearItem = (EquipableItemType) entry.getValue();

                    modifiers.addAll(gearItem.getModifiers());
                    skillModifiers.addAll(gearItem.getSkillModifiers());

                    if (gearItem instanceof SocketedEquipableItemType withSockets) {
                        GearGemSocketsState state = withSockets.getState(itemStack);

                        if (state == null) {
                            return;
                        }


                        for (Gem gem : state.getInsertedGems()) {
                            modifiers.addAll(gem.getModifiers());
                            skillModifiers.addAll(gem.getSkillModifiers());
                        }

                    }

                    if (gearItem instanceof Fairy fairy) {
                        FairyState state = fairy.getState(itemStack);

                        if (state == null) {
                            return;
                        }

                        for (FairyFruit upgrade : state.getInsertedUpgrades()) {
                            modifiers.addAll(upgrade.getModifiers());
                            skillModifiers.addAll(upgrade.getSkillModifiers());
                        }

                    }
                });

        return Map.entry(modifiers, skillModifiers);
    }
}
