package org.zuma.examplePlugin.domain.item.impl.consumable;

import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.core.constants.Colors;
import org.zuma.examplePlugin.core.interfaces.Serializable;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItem;
import org.zuma.examplePlugin.domain.item.constants.ItemId;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.meta.PotionMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.states.MultiConsumableState;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.events.item.PlayerConsumableConsumeEvent;

public class PotionItem extends AbstractItem implements Stateful, Serializable {
    private int usages;
    private int maxUsages;
    private String someEffect;

    public PotionItem(AbstractItemMeta meta) {
        super(meta);
        this.setRarityPrefixSlot("\uD83E\uDDEA");
        this.setStateType(StateType.MULTI_CONSUMABLE);
    }


    @Override
    public AbstractState getInitialState() {
        return new MultiConsumableState(maxUsages, usages);
    }

    @Override
    public void deserializeAndApplyMeta() {
        if (jsonMeta == null) {
            return;
        }

        if (super.getJsonMetaVersion() == 1) {
            PotionMeta meta = new Gson().fromJson(super.jsonMeta, PotionMeta.class);
            usages = meta.usages();
            maxUsages = meta.maxUsages();
            someEffect = meta.someEffect();
            setCustomModelData(meta.potionModel());
            return;
        }

        throw new Error("[" + this.displayName + "] Unsupported JSON meta version! " + super.getJsonMetaVersion());
    }

    @Override
    public void onPlayerConsumeItem(PlayerItemConsumeEvent event, RunePlayer player, StateManager itemStateManager) {
        ItemStack itemStack = PlayerUtils.getItemStackInHand(player.getPlayer());
        MultiConsumableState state = (MultiConsumableState) itemStateManager.getItemState(itemStack);

        if (state == null) {
            return;
        }

        if (state.isConsumed()) {
            player.getPlayer().sendMessage(Component.text("It's consumed already!", Colors.ERROR.asColor()));
            return;
        }

        PlayerConsumableConsumeEvent newEvent = new PlayerConsumableConsumeEvent(player, this, itemStack, itemStateManager);
        Bukkit.getServer().getPluginManager().callEvent(newEvent);

        if (newEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        if (state.consume()) {
            this.setDisplayName(itemStack, null, Component.text(" (" + state.getUsageLeft() + "/" + state.getMaxUsages() + ")", Colors.ITEM_TYPE.asColor()));
            itemStateManager.saveUpdatedState(state);

            if (state.isConsumed()) {
                PlayerUtils.subtractOneFromMainHand(player);
                player.addItemOrDrop(new ItemRecord(ItemId.EMPTY_POTION_BOTTLE, 1));
            }
        }
    }


}
