package org.zuma.examplePlugin.domain.mob.impl;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.entity.EntityTargetEvent;
import org.zuma.examplePlugin.domain.mob.behavior.MobBehavior;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.player.RunePlayer;
import org.zuma.examplePlugin.domain.skill.SkillLevelRecord;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// A combatant mob that sends a random message to a player the first time it targets them.
public class TalkingCombatant extends GenericCombatant {
    private final List<UUID> messagesSentTo = new ArrayList<>(); // To track players who have already received messages

    @Nullable
    private List<String> attackMessages = null;

    public TalkingCombatant(String displayName,
                            @Nullable EquipmentSet equipmentSet,
                            List<SkillLevelRecord> skills,
                            List<Modifier> modifiers,
                            @Nullable MobBehavior behavior,
                            double walkSpeed,
                            int health,
                            @Nullable List<String> attackMessages,
                            @Nullable String skin,
                            @Nullable String model,
                            @Nullable DisguiseType disguiseType,
                            List<Integer> dropTableIds) {
        super(displayName, equipmentSet, skills, modifiers, behavior, walkSpeed, true, false, health, skin, model, disguiseType, dropTableIds, true);
        this.attackMessages = attackMessages;
    }

    @Override
    public void onEntityTargetPlayer(EntityTargetEvent event, RunePlayer player) {
        if (this.getBehavior() != null) {
            this.getBehavior().playSound(event.getEntity(), MobBehavior.MobSoundType.NEGATIVE);
        }

        if (this.attackMessages == null || this.messagesSentTo.contains(player.getUUID())) return;

        String randomMessage = attackMessages.get((int) (Math.random() * attackMessages.size()));
        player.asBukkitPlayer().sendMessage(Component.text(this.getDisplayName(), NamedTextColor.WHITE)
                .append(Component.text(" » ", TextColor.color(150, 150, 150)))
                .append(Component.text(randomMessage, NamedTextColor.WHITE)));

        messagesSentTo.add(player.getUUID());
    }
}
