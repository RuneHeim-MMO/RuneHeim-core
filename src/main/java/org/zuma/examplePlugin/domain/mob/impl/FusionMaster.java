package org.zuma.examplePlugin.domain.mob.impl;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.zuma.examplePlugin.domain.dialog.DialogBuilder;
import org.zuma.examplePlugin.domain.gui.constants.InventoryId;
import org.zuma.examplePlugin.domain.mob.base.AbstractInventoryOpener;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.meta.FriendlyImmortalMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FusionMaster extends AbstractInventoryOpener {
    public FusionMaster(String displayName, MobBehavior behavior, int maxWalkDistance) {
        super(new FriendlyImmortalMobMeta(Component.text(displayName), EntityType.SHEEP, true), InventoryId.FUSION_MASTER.asInt(), null, null, maxWalkDistance);
        super.setDisguise(DisguiseType.WARDEN);
        super.setBehavior(behavior);

        List<String> actions = Arrays.asList(
                "stares you dead in the eyes",
                "locks gaze with you",
                "fixes you with an unblinking stare"
        );

        super.attachDialog(() -> DialogBuilder.deserialize("{\"uuid\":\"71ba3402-69b2-485e-9e4f-37f95ad8ed65\",\"id\":\"fusion-master\",\"steps\":[{\"uuid\":\"fdd9283a-4880-49a6-990b-294cb62c9306\",\"uniqueIdentifier\":\"567f63ef-a7b8-47bf-8543-d0b746f9be2a\",\"type\":\"PassiveDialogStep\",\"content\":[{\"line\":\"§4*" + actions.get(new Random().nextInt(actions.size())) + "*\",\"typewriter\":false}],\"id\":\"fusion-master\",\"actions\":[\"dialog_holder_forward_interaction\"]}]}"));
    }

    @Override
    public void onInventoryClose(RunePlayer player) {
        super.playSound(MobBehavior.MobSoundType.NEGATIVE);
    }
}
