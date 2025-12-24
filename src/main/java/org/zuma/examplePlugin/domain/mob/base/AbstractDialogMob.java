package org.zuma.examplePlugin.domain.mob.base;

import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.zuma.examplePlugin.RuneQuestPlugin;
import org.zuma.examplePlugin.core.config.ConfigManager;
import org.zuma.examplePlugin.core.config.EnvironmentType;
import org.zuma.examplePlugin.core.utils.PlayerUtils;
import org.zuma.examplePlugin.domain.dialog.Dialog;
import org.zuma.examplePlugin.domain.dialog.DialogBuilder;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyImmortalMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public abstract class AbstractDialogMob extends FriendlyImmortalMob {
    private Supplier<DialogBuilder> dialogTemplate;
    private int activeDialogs;

    public AbstractDialogMob(FriendlyImmortalMobMeta meta, @Nullable String skin, @Nullable String model, int maxWalkDistance, Supplier<DialogBuilder> dialog) {
        super(meta);
        this.dialogTemplate = dialog;
        this.activeDialogs = 0;

        if (skin != null) setPlayerDisguise(skin);
        if (model != null) setModel(model);
        setMaxWalkDistance(maxWalkDistance);
    }

    public void setDialogTemplate(Supplier<DialogBuilder> dialogTemplate) {
        this.dialogTemplate = dialogTemplate;
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        event.setCancelled(true);

        if (player.isInDialog()) {
            return;
        }

        super.playSound(MobBehavior.MobSoundType.IDLE);

        if (dialogTemplate != null) {
            if (RuneQuestPlugin.getDialogHandler().getActivePlayerDialog(player) != null) {
                return;
            }

            DialogBuilder newDialogTemplate = dialogTemplate.get();

            if (!ConfigManager.environment.equals(EnvironmentType.PRODUCTION)) {
                System.out.println(newDialogTemplate.serialize());
            }

            Dialog dialog = newDialogTemplate.build(super.getUUID());

            freezeMob();
            activeDialogs++;


            RuneQuestPlugin.getDialogHandler().startDialog(player, dialog, () -> {
                // Decrease active dialog count but not below 0
                activeDialogs = Math.max(0, activeDialogs - 1);
                // Only unfreeze if no active dialogs remain
                if (activeDialogs == 0) unfreezeMob();
            });
            return;
        }

        // If no dialog, continue
        handleInteraction(player);

    }

}
