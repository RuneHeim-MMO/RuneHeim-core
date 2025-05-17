package org.zuma.examplePlugin.item.base;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.interfaces.WithRequirements;
import org.zuma.examplePlugin.item.SkillRequirement;
import org.zuma.examplePlugin.item.constants.ItemRarity;
import org.zuma.examplePlugin.item.constants.ItemType;
import org.zuma.examplePlugin.modifier.Modifier;

import java.util.ArrayList;
import java.util.List;

public abstract class GearItem extends CustomItem implements WithRequirements {
    private final List<Modifier> modifiers = new ArrayList<>();
    private final List<SkillRequirement> requirements = new ArrayList<>();

    public GearItem(int id, Material material, ItemRarity rarity, String displayName) {
        this(id, material, rarity, displayName, null);
    }

    public GearItem(int id, Material material, ItemRarity rarity, String displayName, List<Component> lore) {
        super(ItemType.GEAR, id, material, rarity, displayName, lore);
        this.setCustomModelData(id);
        this.setRarityPrefixSlot("⚔");
    }

    public List<Modifier> getModifiers() {
        return this.modifiers;
    }

    public void addModifier(Modifier modifier) {
        modifiers.add(modifier);
    }

    public @NotNull List<SkillRequirement> getRequirements() {
        return requirements;
    }

    public void addRequirement(SkillRequirement requirement) {
        requirements.add(requirement);
    }

}
