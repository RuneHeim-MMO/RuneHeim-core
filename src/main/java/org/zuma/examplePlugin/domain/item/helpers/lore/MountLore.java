package org.zuma.examplePlugin.domain.item.helpers.lore;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.domain.item.impl.Mount;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.MountState;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MountLore implements LoreCreator {
    private final List<Component> lore = new ArrayList<>();

    public MountLore(Mount mount, ItemStack item) {
        AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);

        if (state instanceof MountState mountState) {
            int ageInDays = (int) TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - state.getCreatedAt());

            lore.add(Component.empty());

            lore.add(Icon.LEVEL.asComponent().append(Component.text("Level: ", Color.ITEM_LORE_DESCRIPTION.asColor())
                    .append(Component.text(mountState.getLevel(mount) + " (" + mount.getMaxLevel() + " max level)", Color.ACCENT.asColor()))));

            String formattedCurrentXP = NumberFormat.getNumberInstance().format(mountState.getExperience());
            String formattedNextLevelXP = NumberFormat.getNumberInstance().format(mountState.getXPForLevel(mount, mountState.getLevel(mount) + 1));
            String formattedDistanceCovered = NumberFormat.getNumberInstance().format((int) mountState.getDistanceCovered());
            String formattedAgeDays = NumberFormat.getNumberInstance().format(ageInDays);

            if (mountState.isMaxed(mount)) {
                lore.add(Icon.EXPERIENCE.asComponent()
                        .append(Component.text("Maximum level achieved", Color.SUCCESS.asColor())));
            } else {
                lore.add(Icon.EXPERIENCE.asComponent()
                        .append(Component.text(formattedCurrentXP + " XP / " + formattedNextLevelXP + " XP", Color.SUCCESS.asColor())));
            }

            lore.add(Component.empty());

            if (mountState.getName() != null) {
                lore.add(Icon.NAME_TAG.asComponent().append(Component.text(mountState.getName(), Color.ITEM_LORE_DESCRIPTION.asColor())));
            }

            lore.add(Icon.DISTANCE_TRAVELED.asComponent().append(Component.text("Distance: ", Color.ITEM_LORE_DESCRIPTION.asColor())
                    .append(Component.text(formattedDistanceCovered + " blocks", Color.ACCENT.asColor()))));

            lore.add(Icon.CLOCK.asComponent().append(Component.text("Age: ", Color.ITEM_LORE_DESCRIPTION.asColor())
                    .append(Component.text(formattedAgeDays + (ageInDays == 1 ? " day" : " days"), Color.ACCENT.asColor()))));

        }
    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
