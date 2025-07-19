package org.zuma.examplePlugin.domain.mob.impl;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.zuma.examplePlugin.domain.mob.base.CombatantMob;
import org.zuma.examplePlugin.domain.mob.constants.EquipmentSet;
import org.zuma.examplePlugin.domain.mob.meta.CombatantMobMeta;
import org.zuma.examplePlugin.domain.player.RunePlayer;

import javax.annotation.Nullable;
import java.util.List;

import static org.zuma.examplePlugin.domain.mob.MobTypesLoader.getBaseSkills;

public class CityGuardMob extends GenericCombatant {
    public CityGuardMob(String displayName, @Nullable String skin, @Nullable String model,
                        @Nullable DisguiseType disguiseType, int walkDistance) {
        super(displayName, EquipmentSet.RUNE_SET_BATTLEAXE, getBaseSkills(99), null, null, 0.5, false, false, 9999, skin, model, disguiseType, List.of());

        setPlayerDisguise("Notch");
        setMaxWalkDistance(walkDistance);
        setBaseEntity(EntityType.IRON_GOLEM);
        setSilent(false);
    }

    @Override
    public void onPlayerAttackEntity(EntityDamageByEntityEvent event, RunePlayer player) {
        event.setCancelled(true);

    }
}
