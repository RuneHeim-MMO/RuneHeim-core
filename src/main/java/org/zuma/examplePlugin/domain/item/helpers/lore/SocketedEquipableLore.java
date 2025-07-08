package org.zuma.examplePlugin.domain.item.helpers.lore;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Color;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.utils.TextUtils;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.GearGemSocketsState;

import java.util.ArrayList;
import java.util.List;

public class SocketedEquipableLore implements LoreCreator {
    private final List<Component> lore = new ArrayList<>();

    public SocketedEquipableLore(ItemStack item) {
        AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);

        if (state instanceof GearGemSocketsState socketsState) {
            lore.add(Component.empty());
            lore.add(Icon.GREEN_GEM.asComponent().append(Component.text("Gem slots (" + socketsState.getInsertedGemIds().size() + "/" + socketsState.getTotalGemSlots() + ")", Color.SUCCESS.asColor())));
            lore.add(Component.text(TextUtils.repeatString("-", 16), Color.SUCCESS.asColor()).decorate(TextDecoration.STRIKETHROUGH));

            socketsState.getInsertedGems().forEach(gemItem -> {
                TextColor rarityColor = TextColor.fromHexString(gemItem.rarity.getHexColor());
                lore.add(Component.text(" ● " + gemItem.displayName, rarityColor));
            });

            for (int i = 0; i < socketsState.getTotalGemSlots() - socketsState.getInsertedGemIds().size(); i++) {
                lore.add(Component.text(" * Empty gem slot *", Color.ITEM_TYPE_LABEL.asColor()));
            }
            lore.add(Component.text(TextUtils.repeatString("-", 16), Color.SUCCESS.asColor()).decorate(TextDecoration.STRIKETHROUGH));
            lore.add(Component.empty());
        }
    }

    @Override
    public List<Component> getLore() {
        return lore;
    }
}
