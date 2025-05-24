package org.zuma.examplePlugin.domain.item.base;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.domain.item.SkillRequirement;
import org.zuma.examplePlugin.domain.item.interfaces.Equipable;
import org.zuma.examplePlugin.domain.item.interfaces.Serializable;
import org.zuma.examplePlugin.domain.item.meta.AbstractMeta;
import org.zuma.examplePlugin.domain.item.meta.EquipableMeta;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipableItem extends AbstractItem implements Equipable, Serializable {
    private final List<Modifier> modifiers = new ArrayList<>();
    private final List<SkillRequirement> requirements = new ArrayList<>();

    public EquipableItem(AbstractMeta meta) {
        super(meta);
        this.setRarityPrefixSlot("⚔");
    }

    public List<Modifier> getModifiers() {
        return this.modifiers;
    }

    public Optional<Modifier> getModifierByType(ModifierType type) {
        return this.modifiers.stream()
                .filter(modifier -> modifier.type() == type)
                .findFirst();
    }

    public @NotNull List<SkillRequirement> getRequirements() {
        return requirements;
    }

    public void deserializeAndApplyMeta() {
        EquipableMeta deserialized = new Gson().fromJson(this.jsonMeta, EquipableMeta.class);

        modifiers.addAll(deserialized.modifiers());
        requirements.addAll(deserialized.requirements());
        requirements.forEach(requirement -> appendToRequirementsLore(Component.text(requirement.getType().getSymbol() + " " + requirement.getMinimumLevel() + " " + requirement.getType().getLabel()).color(NamedTextColor.GRAY)));
    }

}
