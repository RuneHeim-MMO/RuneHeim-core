package org.zuma.examplePlugin.domain.mob.base;

import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.dialog.Dialog;
import org.zuma.examplePlugin.domain.dialog.DialogBuilder;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyImmortalMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public abstract class AbstractDialogMob extends FriendlyImmortalMob {
    private final Supplier<DialogBuilder> dialogTemplate;

    public AbstractDialogMob(FriendlyImmortalMobMeta meta, @Nullable String skin, @Nullable String model, int maxWalkDistance, Supplier<DialogBuilder> dialog) {
        super(meta);
        this.dialogTemplate = dialog;

        if (skin != null) setPlayerDisguise(skin);
        if (model != null) setModel(model);
        setMaxWalkDistance(maxWalkDistance);
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        event.setCancelled(true);
        super.playSound(MobBehavior.MobSoundType.IDLE);

        if (dialogTemplate != null) {
            if (RuneQuestPlugin.getDialogHandler().getActivePlayerDialog(player) != null) {
                return;
            }

            DialogBuilder newDialogTemplate = dialogTemplate.get();
            System.out.println(newDialogTemplate.serialize());
            Dialog dialog = newDialogTemplate.build(super.getUUID());
            RuneQuestPlugin.getDialogHandler().startDialog(player, dialog, this::unfreezeMob);
            return;
        }

        // If no dialog, continue
        handleInteraction(player);

    }


    public abstract void onInventoryClose(RunePlayer player);
}
