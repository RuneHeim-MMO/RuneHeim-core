package org.zuma.examplePlugin.domain.item.base;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.core.interfaces.Serializable;
import org.zuma.examplePlugin.core.interfaces.SkillRestrictable;
import org.zuma.examplePlugin.domain.item.meta.AbstractMeta;
import org.zuma.examplePlugin.domain.item.meta.EquipableMeta;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;
import org.zuma.examplePlugin.domain.skill.SkillRequirement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EquipableItem extends AbstractItem implements SkillRestrictable, Serializable {
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
        if (super.getJsonMetaVersion() == 1) {
            EquipableMeta deserialized = new Gson().fromJson(this.jsonMeta, EquipableMeta.class);

            modifiers.addAll(deserialized.modifiers());
            requirements.addAll(deserialized.requirements());

            // Lore
            requirements.forEach(requirement -> appendToRequirementsLore(Component.text(requirement.getType().getSymbol() + " " + requirement.getMinimumLevel() + " " + requirement.getType().getLabel()).color(Colors.ITEM_REQUIREMENT.asColor())));
            modifiers.forEach(modifier -> {
                int amount = Math.toIntExact(Math.round(modifier.amount()));
                String sign = amount > 0 ? "+" : "";
                TextColor color = amount > 0 ? Colors.ITEM_BONUS_POSITIVE.asColor() : Colors.ITEM_BONUS_NEGATIVE.asColor();

                appendToBonusesLore(Component.text(modifier.type().getSymbol() + " " + sign + amount + " " + modifier.type().getLabel()).color(color));
            });

            return;
        }

        throw new Error("[" + this.displayName + "] Unsupported JSON meta version! " + super.getJsonMetaVersion());
    }
}
