package org.zuma.examplePlugin.domain.item.impl;

import org.bukkit.event.player.PlayerInteractEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.constants.Icon;
import org.zuma.examplePlugin.core.constants.SoundType;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.item.ItemRecord;
import org.zuma.examplePlugin.domain.item.base.AbstractItemType;
import org.zuma.examplePlugin.domain.item.meta.AbstractItemMeta;
import org.zuma.examplePlugin.domain.item.state.StateManager;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.player.actions.ActionParams;
import org.zuma.examplePlugin.domain.player.actions.ProgressBarAction;
import org.zuma.examplePlugin.domain.skill.constants.SkillType;

import java.util.List;

public class Remains extends AbstractItemType {
    private double buryXPReward;

    public Remains(AbstractItemMeta meta) {
        super(meta);
        super.setTypeLore("Remains", Icon.REMAINS.get());
    }

    public void setBuryXPReward(double amount) {
        this.buryXPReward = amount;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event, RunePlayer player, StateManager itemStateManager) {
        if (player.isBusy()) return;

        ActionParams params = new ActionParams.Builder()
                .durationMs(1000)
                .actionLabel("Burying " + this.displayName)
                .successActionLabel("")
                .stepSound(SoundType.BONES_BURY_DIG)
                .stepSoundEveryNth(3)
                .onSuccess(() -> handleBury(player))
                .build();

        player.startAction(new ProgressBarAction(true, false), params);
    }

    private void handleBury(RunePlayer player) {
        if (!PlayerUtils.removeItems(player, List.of(new ItemRecord(this.id, 1)))) {
            return;
        }

        SoundType.BONES_BURY_FINISH.play(player, false);
        SoundType.BONES_BURY_DIG.play(player, true);
        player.distributeXp(SkillType.PRAYER, buryXPReward);
    }

}
