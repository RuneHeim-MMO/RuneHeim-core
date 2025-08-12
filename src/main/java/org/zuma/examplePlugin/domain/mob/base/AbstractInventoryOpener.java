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

public abstract class AbstractInventoryOpener extends FriendlyImmortalMob {
    private Supplier<DialogBuilder> dialogTemplate;
    private final int inventoryId;

    public AbstractInventoryOpener(FriendlyImmortalMobMeta meta, int inventoryId, @Nullable String skin, @Nullable String model, int maxWalkDistance) {
        super(meta);
        this.inventoryId = inventoryId;

        if (skin != null) setPlayerDisguise(skin);
        if (model != null) setModel(model);
        setMaxWalkDistance(maxWalkDistance);
    }

    public AbstractInventoryOpener attachDialog(Supplier<DialogBuilder> template) {
        this.dialogTemplate = template;
        return this;
    }

    @Override
    public void handleInteraction(RunePlayer player) {
        RuneQuestPlugin.getGUIHandler().openInventory(inventoryId, player, () -> {
            onInventoryClose(player);
            unfreezeMob();
        }, this, null);
    }

    @Override
    public void onPlayerInteractWithEntity(PlayerInteractAtEntityEvent event, RunePlayer player) {
        if (PlayerUtils.hasCustomInventoryOpen(player)) {
            return;
        }

        event.setCancelled(true);
        super.playSound(MobBehavior.MobSoundType.IDLE);
        freezeMob();

        if (dialogTemplate != null) {
            DialogBuilder newDialogTemplate = dialogTemplate.get();
            System.out.println(newDialogTemplate.serialize());
            Dialog dialog = newDialogTemplate.build(super.getUUID());
            RuneQuestPlugin.getDialogHandler().startDialog(player, dialog, this::unfreezeMob);
            return;
        }

        // If no dialog, continue
        handleInteraction(player);

    }

    public int getInventoryId() {
        return this.inventoryId;
    }

    public abstract void onInventoryClose(RunePlayer player);
}
