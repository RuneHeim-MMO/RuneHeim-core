package org.zuma.examplePlugin.domain.item.helpers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.base.EquipableItemType;
import org.zuma.examplePlugin.domain.item.base.SocketedEquipableItemType;
import org.zuma.examplePlugin.domain.item.constants.ItemRarity;
import org.zuma.examplePlugin.domain.item.constants.PersistanceDataKey;
import org.zuma.examplePlugin.domain.item.impl.consumable.Food;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.GearGemSocketsState;
import org.zuma.examplePlugin.domain.item.state.states.MultiConsumableState;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MetaGenerator {
    private final AbstractItemType itemType;

    public MetaGenerator(AbstractItemType itemType) {
        this.itemType = itemType;
    }

    public void updatePresentationLayer(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.lore(generateLore(item));
        meta.displayName(getDisplayName(item));
        item.setItemMeta(meta);
    }

    public List<Component> generateLore(ItemStack item) {
        List<Component> lore = new ArrayList<>();

        if (itemType == null) {
            return lore;
        }

        lore.addAll(itemType.descriptionLore.stream().map(str -> Component.text(str).color(Color.ITEM_DESCRIPTION.asColor())).toList());

        if (itemType instanceof Food food) {
            lore.add(Component.text("Heals ", Color.SUCCESS.asColor())
                    .append(SkillType.HITPOINTS.getSymbolComponent())
                    .append(Component.text(" " + food.getHealAmount() + " " + SkillType.HITPOINTS.getLabel(), Color.SUCCESS.asColor())));
        }

        if (itemType instanceof EquipableItemType equipable) {
            lore.add(Component.empty());

            if (!equipable.getRequirements().isEmpty()) {
                lore.add(Component.text("Requirements: ", Color.ITEM_LABEL.asColor()));

                equipable.getRequirements().forEach(requirement -> lore.add(
                        Component.text(requirement.type().getSymbol(), NamedTextColor.WHITE)
                                .append(Component.text(" " + requirement.minimumLevel() + " " + requirement.type().getLabel(), Color.ITEM_REQUIREMENT.asColor()))));

            }

            if (!equipable.getModifiers().isEmpty()) {
                lore.add(Component.empty());
                lore.add(Component.text("Modifiers: ", Color.ITEM_LABEL.asColor()));

                equipable.getModifiers().forEach(modifier -> {
                    int amount = Math.toIntExact(Math.round(modifier.amount()));
                    String sign = amount > 0 ? "+" : "";
                    TextColor color = amount > 0 ? Color.ITEM_BONUS_POSITIVE.asColor() : Color.ITEM_BONUS_NEGATIVE.asColor();

                    lore.add(Component.text(modifier.type().getSymbol(), NamedTextColor.WHITE)
                            .append(Component.text(" " + sign + amount + " " + modifier.type().getLabel()).color(color)));
                });
            }
        }

        if (itemType instanceof SocketedEquipableItemType) {
            AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);
            System.out.println("SOCKET EQUIPABLE TYPE " + state);
            if (state instanceof GearGemSocketsState socketsState) {
                System.out.println("SOCKET EQUIPABLE TYPE@@@@@@@@@");
                lore.add(Component.empty());
                lore.add(Icon.GREEN_GEM.asComponent().append(Component.text("Gem slots (0/" + socketsState.getTotalGemSlots() + ")", Color.SUCCESS.asColor())));
                lore.add(Component.text(TextUtils.repeatString("-", 16), Color.SUCCESS.asColor()).decorate(TextDecoration.STRIKETHROUGH));

                socketsState.getInsertedGems().forEach(gemItem -> {
                    // Todo: gem based colors?
                    lore.add(Component.text("* " + gemItem.displayName, Color.SUCCESS.asColor()));
                });

                for (int i = 0; i < socketsState.getTotalGemSlots() - socketsState.getInsertedGemIds().size(); i++) {
                    lore.add(Component.text(" * Empty gem slot *", Color.ITEM_TYPE.asColor()));
                }
                lore.add(Component.text(TextUtils.repeatString("-", 16), Color.SUCCESS.asColor()).decorate(TextDecoration.STRIKETHROUGH));
                lore.add(Component.empty());
            }

        }

        lore.add(Component.empty());
        lore.addAll(itemType.typeLore);

        if (itemType.rarity != ItemRarity.NOT_APPLICABLE) {
            lore.add(itemType.rarity.getComponent().decoration(TextDecoration.ITALIC, false));
        }

        return lore.stream().map(component -> component.decoration(TextDecoration.ITALIC, false)).toList();
    }

    public Component getDisplayNameSuffix(ItemStack item) {
        if (!(itemType instanceof Stateful)) {
            return Component.empty();
        }
        AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);

        if (state instanceof MultiConsumableState consumableState) {
            return Component.text(" (" + consumableState.getUsageLeft() + "/" + consumableState.getMaxUsages() + ")", Color.ITEM_TYPE.asColor());
        }

        return Component.empty();
    }

    public Component getDisplayName(ItemStack item) {
        TextColor rarityColor = TextColor.fromHexString(itemType.rarity.getHexColor());
        ItemMeta meta = item.getItemMeta();
        Component originalName = meta.displayName();
        Component prefix = Component.empty();
        Component suffix = getDisplayNameSuffix(item);

        if (itemType.displayName != null) {
            Component base = Component.text(itemType.displayName)
                    .color(rarityColor)
                    .decoration(TextDecoration.ITALIC, false);

            base = base.append(prefix.color(rarityColor).decoration(TextDecoration.ITALIC, false));
            base = base.append(suffix.color(rarityColor).decoration(TextDecoration.ITALIC, false));

            return base;

        } else if (originalName != null) {
            Component base = originalName
                    .color(rarityColor)
                    .decoration(TextDecoration.ITALIC, false);

            base = base.append(prefix.color(rarityColor).decoration(TextDecoration.ITALIC, false));
            base = base.append(suffix.color(rarityColor).decoration(TextDecoration.ITALIC, false));

            return base;
        }

        return item.displayName();
    }

    public void setItemStackProps(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        NamespacedKey IdKey = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistanceDataKey.ITEM_ID.asString());
        meta.getPersistentDataContainer().set(IdKey, PersistentDataType.INTEGER, itemType.id);

        if (itemType.stateType != null) {
            NamespacedKey stateUuid = new NamespacedKey(RuneQuestPlugin.getInstance(), PersistanceDataKey.STATE_UUID.asString());
            meta.getPersistentDataContainer().set(stateUuid, PersistentDataType.STRING, UUID.randomUUID().toString());
        }

        // To set it's state uuid so lore generation fn can access it's state.
        item.setItemMeta(meta);

        if (itemType.customModelData != -1) {
            meta.setCustomModelData(itemType.customModelData);
        }

        meta.displayName(getDisplayName(item));
        meta.lore(generateLore(item));
        item.setItemMeta(meta);

    }
}
