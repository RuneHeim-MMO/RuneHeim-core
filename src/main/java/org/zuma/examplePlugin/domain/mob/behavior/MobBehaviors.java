package org.zuma.examplePlugin.domain.mob.behavior;

import org.zuma.examplePlugin.core.constants.SoundType;

import java.util.List;

public enum MobBehaviors {
    VILLAGER_MAN_1(new MobBehavior(
            List.of(SoundType.VILLAGER_MAN_AMBIENT_1),
            List.of(SoundType.VILLAGER_MAN_CELEBRATE_1),
            List.of(SoundType.VILLAGER_MAN_NO_1),
            List.of(SoundType.VILLAGER_MAN_HURT_1),
            List.of(SoundType.VILLAGER_MAN_DEATH_1)
    )),

    VILLAGER_MAN_2(new MobBehavior(
            List.of(SoundType.VILLAGER_MAN_AMBIENT_2),
            List.of(SoundType.VILLAGER_MAN_CELEBRATE_2),
            List.of(SoundType.VILLAGER_MAN_NO_2),
            List.of(SoundType.VILLAGER_MAN_HURT_2),
            List.of(SoundType.VILLAGER_MAN_DEATH_2)
    )),

    VILLAGER_MAN_3(new MobBehavior(
            List.of(SoundType.VILLAGER_MAN_AMBIENT_3),
            List.of(SoundType.VILLAGER_MAN_CELEBRATE_3),
            List.of(SoundType.VILLAGER_MAN_NO_3),
            List.of(SoundType.VILLAGER_MAN_HURT_3),
            List.of(SoundType.VILLAGER_MAN_DEATH_3)
    )),

    VILLAGER_MAN_4(new MobBehavior(
            List.of(SoundType.VILLAGER_MAN_AMBIENT_4),
            List.of(SoundType.VILLAGER_MAN_CELEBRATE_4),
            List.of(SoundType.VILLAGER_MAN_NO_4),
            List.of(SoundType.VILLAGER_MAN_HURT_4),
            List.of(SoundType.VILLAGER_MAN_DEATH_4)
    )),

    VILLAGER_MAN_5(new MobBehavior(
            List.of(SoundType.VILLAGER_MAN_AMBIENT_5),
            List.of(SoundType.VILLAGER_MAN_CELEBRATE_5),
            List.of(SoundType.VILLAGER_MAN_NO_5),
            List.of(SoundType.VILLAGER_MAN_HURT_5),
            List.of(SoundType.VILLAGER_MAN_DEATH_5)
    )),

    // WOMAN variations
    VILLAGER_WOMAN_1(new MobBehavior(
            List.of(SoundType.VILLAGER_WOMAN_AMBIENT_1),
            List.of(SoundType.VILLAGER_WOMAN_CELEBRATE_1),
            List.of(SoundType.VILLAGER_WOMAN_NO_1),
            List.of(SoundType.VILLAGER_WOMAN_HURT_1),
            List.of(SoundType.VILLAGER_WOMAN_DEATH_1)
    )),

    VILLAGER_WOMAN_2(new MobBehavior(
            List.of(SoundType.VILLAGER_WOMAN_AMBIENT_2),
            List.of(SoundType.VILLAGER_WOMAN_CELEBRATE_2),
            List.of(SoundType.VILLAGER_WOMAN_NO_2),
            List.of(SoundType.VILLAGER_WOMAN_HURT_2),
            List.of(SoundType.VILLAGER_WOMAN_DEATH_2)
    )),

    VILLAGER_WOMAN_3(new MobBehavior(
            List.of(SoundType.VILLAGER_WOMAN_AMBIENT_3),
            List.of(SoundType.VILLAGER_WOMAN_CELEBRATE_3),
            List.of(SoundType.VILLAGER_WOMAN_NO_3),
            List.of(SoundType.VILLAGER_WOMAN_HURT_3),
            List.of(SoundType.VILLAGER_WOMAN_DEATH_3)
    )),

    VILLAGER_WOMAN_4(new MobBehavior(
            List.of(SoundType.VILLAGER_WOMAN_AMBIENT_4),
            List.of(SoundType.VILLAGER_WOMAN_CELEBRATE_4),
            List.of(SoundType.VILLAGER_WOMAN_NO_4),
            List.of(SoundType.VILLAGER_WOMAN_HURT_4),
            List.of(SoundType.VILLAGER_WOMAN_DEATH_4)
    )),

    VILLAGER_WOMAN_5(new MobBehavior(
            List.of(SoundType.VILLAGER_WOMAN_AMBIENT_5),
            List.of(SoundType.VILLAGER_WOMAN_NO_5),
            List.of(SoundType.VILLAGER_WOMAN_NO_5),
            List.of(SoundType.VILLAGER_WOMAN_HURT_5),
            List.of(SoundType.VILLAGER_WOMAN_DEATH_5)
    )),

    FUSION_MASTER(new MobBehavior(
            List.of(SoundType.FUSION_MASTER_AMBIENT_1, SoundType.FUSION_MASTER_AMBIENT_2, SoundType.FUSION_MASTER_AMBIENT_3),
            List.of(SoundType.FUSION_MASTER_SUCCESS),
            List.of(SoundType.FUSION_MASTER_NO),
            null,
            null
    )),

    GOBLIN(new MobBehavior(
            List.of(SoundType.GOBLIN_AMBIENT),
            List.of(SoundType.GOBLIN_CELEBRATE),
            List.of(SoundType.GOBLIN_NO),
            List.of(SoundType.GOBLIN_HURT),
            List.of(SoundType.GOBLIN_DEATH)
    )),

    SHEEP(new MobBehavior(
            List.of(SoundType.SHEEP_AMBIENT),
            List.of(SoundType.SHEEP_SUCCESS),
            List.of(SoundType.SHEEP_NO),
            List.of(SoundType.SHEEP_HURT),
            List.of(SoundType.SHEEP_DEATH)
    )),

    BOAR(new MobBehavior(
            List.of(SoundType.GOBLIN_AMBIENT),
            List.of(SoundType.GOBLIN_CELEBRATE),
            List.of(SoundType.GOBLIN_NO),
            List.of(SoundType.GOBLIN_HURT),
            List.of(SoundType.GOBLIN_DEATH)
    )),

    WOLF(new MobBehavior(
            List.of(SoundType.GOBLIN_AMBIENT),
            List.of(SoundType.GOBLIN_CELEBRATE),
            List.of(SoundType.GOBLIN_NO),
            List.of(SoundType.GOBLIN_HURT),
            List.of(SoundType.GOBLIN_DEATH)
    ));


    private final MobBehavior behavior;

    MobBehaviors(MobBehavior behavior) {
        this.behavior = behavior;
    }

    public MobBehavior getBehavior() {
        return behavior;
    }
}
