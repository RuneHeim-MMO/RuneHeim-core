package org.zuma.examplePlugin.domain.item.impl.utility;

import net.kyori.adventure.text.Component;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.interfaces.Stateful;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.constants.CustomModelId;
import org.zuma.examplePlugin.domain.item.impl.UtilityItem;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.item.state.StateType;
import org.zuma.examplePlugin.domain.item.state.base.AbstractState;
import org.zuma.examplePlugin.domain.item.state.meta.v1.StoneOfPrayMeta;
import org.zuma.examplePlugin.domain.item.state.states.StoneOfPrayState;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.prayer.Prayer;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class StoneOfPray extends UtilityItem implements Stateful {
    private final StoneOfPrayState defaultStateTemplate = new StoneOfPrayState(new StoneOfPrayMeta(new ArrayList<>()));
    private final List<UUID> activeStones;

    public StoneOfPray(AbstractItemMeta meta) {
        super(meta);
        this.setStateType(StateType.STONE_OF_PRAY_STATE);
        this.activeStones = new ArrayList<>();
        this.setCustomModelData(CustomModelId.STONE_OF_PRAY_INACTIVE);
    }

    public AbstractState cloneDefaultStateTemplate() {
        return defaultStateTemplate.clone();
    }

    @Nullable
    public StoneOfPrayState getState(ItemStack item) {
        AbstractState state = RuneQuestPlugin.getItemStateManager().getItemState(item);

        if (state != null && state.getStateType() == StateType.STONE_OF_PRAY_STATE) {
            return (StoneOfPrayState) state;
        }

        return null;
    }

    public void deactivateStone(UUID stateUUID) {
        activeStones.remove(stateUUID);
    }

    private void activateStone(UUID stateUUID, StoneOfPrayState state, RunePlayer player, ItemStack item) {
        if (state.getPreset().isEmpty()) {
            HudService.sendFailMessage(player, Component.text("Stone of Pray preset is empty!"));
            return;
        }

        if (player.getPrayerEnergy() <= 0){
            HudService.sendFailMessage(player, Component.text("You don't have enough prayer energy to activate the Stone of Pray!"));
            return;
        }

        activeStones.add(stateUUID);
        SoundType.STONE_OF_PRAY_ACTIVATE.play(player, false);
        AtomicBoolean canActivateAll = new AtomicBoolean(true);
        List<Prayer> toRemove = new ArrayList<>();

        state.getPreset().forEach(prayer -> {
            if (prayer.getLevelRequirement() > player.getSkill(SkillType.PRAYER).getLevelWithBonuses()) {
                canActivateAll.set(false);
                toRemove.add(prayer);
            } else {
                try {
                    RuneQuestPlugin.getPrayerManager().setPrayer(player, prayer, true);
                } catch (Exception e) {
                    HudService.sendFailMessage(player, Component.text(e.getMessage()));
                }
            }
        });

        HudService.sendInfoMessage(player, Icon.PRAYER.asComponent().append(Component.text("Activated Stone of Pray!")));

        if (!canActivateAll.get()) {
            HudService.sendFailMessage(player, Component.text("Some prayers could not be activated due to level requirements."));
            state.getPreset().removeAll(toRemove);
        }

        setItemStackCustomModelData(item, CustomModelId.STONE_OF_PRAY_ACTIVE);
    }

    private void deactivateStone(UUID stateUUID, StoneOfPrayState state, RunePlayer player, ItemStack item) {
        activeStones.remove(stateUUID);
        SoundType.STONE_OF_PRAY_DEACTIVATE.play(player, false);

        state.getPreset().forEach(prayer -> {
            RuneQuestPlugin.getPrayerManager().setPrayer(player, prayer, false);
        });

        HudService.sendInfoMessage(player, Icon.PRAYER.asComponent().append(Component.text("Deactivated Stone of Pray!")));
        setItemStackCustomModelData(item, CustomModelId.STONE_OF_PRAY_INACTIVE);
    }

    @Override
    public void onPlayerItemDrop(PlayerDropItemEvent event, RunePlayer player, StateManager itemStateManager) {
        ItemStack dropped = event.getItemDrop().getItemStack();
        UUID stateUUID = itemStateManager.getItemStackStateUuid(dropped);

        if (stateUUID == null) {
            return;
        }

        activeStones.remove(stateUUID);
        setItemStackCustomModelData(dropped, CustomModelId.STONE_OF_PRAY_INACTIVE);
        player.asBukkitPlayer().updateInventory();
    }


    @Override
    public void onPlayerInteract(PlayerInteractEvent event, RunePlayer player, StateManager itemStateManager) {
        ItemStack inHand = event.getItem();

        if (inHand == null) {
            return;
        }

        if (player.asBukkitPlayer().isSneaking()) {
            RuneQuestPlugin.getGUIHandler().openInventory(InventoryId.STONE_OF_PRAY_MENU.asInt(), player, null,null);
            SoundType.STONE_OF_PRAY_OPEN.play(player, false);
            return;
        }

        UUID stateUUID = itemStateManager.getItemStackStateUuid(event.getItem());
        AbstractState state = getState(inHand);

        if (stateUUID == null || !(state instanceof StoneOfPrayState stoneOfPrayState)) {
            return;
        }

        if (activeStones.contains(stateUUID)) {
            deactivateStone(stateUUID, stoneOfPrayState, player, inHand);
            return;
        }

        activateStone(stateUUID, stoneOfPrayState, player, inHand);
    }
}
