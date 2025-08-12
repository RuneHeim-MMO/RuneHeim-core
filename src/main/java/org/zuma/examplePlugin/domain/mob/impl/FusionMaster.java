package org.zuma.examplePlugin.domain.mob.impl;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.mob.base.AbstractInventoryOpener;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyImmortalMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

public class FusionMaster extends AbstractInventoryOpener {
    public FusionMaster(String displayName, MobBehavior behavior, int maxWalkDistance) {
        super(new FriendlyImmortalMobMeta(Component.text(displayName), EntityType.VILLAGER, true), InventoryId.FUSION_MASTER.asInt(), null, null, maxWalkDistance);
        super.setDisguise(DisguiseType.WARDEN);
        super.setBehavior(behavior);
    }

    @Override
    public void onInventoryClose(RunePlayer player) {
        super.playSound(MobBehavior.MobSoundType.NEGATIVE);
    }
}
