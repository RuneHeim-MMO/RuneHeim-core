package org.zuma.examplePlugin.domain.item.impl;

import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.analytics.AnalyticsKey;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.logger.constants.LogLevel;
import org.zuma.examplePlugin.core.utils.AnalyticsUtils;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.droptable.DropTable;
import org.zuma.examplePlugin.domain.hud.HudService;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.ArrayList;
import java.util.List;

public class ChanceItem extends AbstractItemType {
    private List<Integer> dropTableIds = new ArrayList<>();
    private SoundType openSound, openFinishSound;

    public ChanceItem(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Chance Item", Icon.CLOVER.get());
    }

    public void setDropTableIds(List<Integer> ids) {
        dropTableIds = ids;
    }

    public void setSounds(SoundType openSound, SoundType rollSound) {
        this.openSound = openSound;
        this.openFinishSound = rollSound;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event, RunePlayer player, StateManager itemStateManager) {
        PlayerUtils.subtractOneFromMainHand(player);

        if (openSound != null) {
            openSound.play(player, true);
        }

        List<ItemRecord> rewards = new ArrayList<>();

        dropTableIds.forEach(tableId -> {
            DropTable matchingTable = RuneQuestPlugin.getDropTableHandler().getByID(tableId);

            if (matchingTable == null) {
                RuneQuestPlugin.getRuneLogger().log(LogLevel.WARN, "Invalid drop table id " + tableId + " in chance item id " + this.id + "!");
                return;
            }

            ItemRecord rollReward = matchingTable.roll(player);

            if (rollReward != null) {
                rewards.add(rollReward);
            }
        });

        AnalyticsUtils.incrementServerAndPlayer(player, AnalyticsKey.CHANCE_ITEMS_USED, 1);

        if (rewards.isEmpty()) {
            HudService.showFailTitle(player, "Bad luck today!");
        } else {
            rewards.forEach(player::giveItemOrDrop);
        }

        if (openFinishSound != null) {
            openFinishSound.play(player, true);
        }
    }

}
