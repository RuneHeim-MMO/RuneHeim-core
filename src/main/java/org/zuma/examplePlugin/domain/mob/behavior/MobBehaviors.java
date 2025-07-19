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
            List.of(SoundType.GOBLIN_ATTACK),
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
            List.of(SoundType.BOAR_AMBIENT_1, SoundType.BOAR_AMBIENT_2),
            List.of(),
            List.of(SoundType.BOAR_ATTACK_1, SoundType.BOAR_ATTACK_2),
            List.of(SoundType.BOAR_HURT_1, SoundType.BOAR_HURT_2),
            List.of(SoundType.BOAR_DEATH_1, SoundType.BOAR_DEATH_2)
    )),

    WOLF(new MobBehavior(
            List.of(SoundType.WOLF_AMBIENT_1, SoundType.WOLF_AMBIENT_2),
            List.of(),
            List.of(SoundType.WOLF_ATTACK_1, SoundType.WOLF_ATTACK_2),
            List.of(SoundType.WOLF_HURT_1, SoundType.WOLF_HURT_2),
            List.of(SoundType.WOLF_DEATH_1, SoundType.WOLF_DEATH_2)
    )),

    CHICKEN(new MobBehavior(
            List.of(SoundType.CHICKEN_AMBIENT),
            List.of(),
            List.of(SoundType.CHICKEN_ATTACK),
            List.of(SoundType.CHICKEN_HURT),
            List.of(SoundType.CHICKEN_DEATH)
    )),

    RAT(new MobBehavior(
            List.of(SoundType.RAT_AMBIENT),
            List.of(),
            List.of(SoundType.RAT_ATTACK),
            List.of(SoundType.RAT_HURT),
            List.of(SoundType.RAT_DEATH)
    )),

    AXOLOTL(new MobBehavior(
            List.of(SoundType.AXOLOTL_AMBIENT),
            List.of(),
            List.of(SoundType.AXOLOTL_ATTACK),
            List.of(SoundType.AXOLOTL_HURT),
            List.of(SoundType.AXOLOTL_DEATH)
    )),

    ARMADILLO(new MobBehavior(
            List.of(SoundType.ARMADILLO_AMBIENT),
            List.of(),
            List.of(SoundType.ARMADILLO_ATTACK),
            List.of(SoundType.ARMADILLO_HURT),
            List.of(SoundType.ARMADILLO_DEATH)
    )),

    BEE(new MobBehavior(
            List.of(SoundType.BEE_AMBIENT),
            List.of(),
            List.of(SoundType.BEE_ATTACK),
            List.of(SoundType.BEE_HURT),
            List.of(SoundType.BEE_DEATH)
    )),

    CAT(new MobBehavior(
            List.of(SoundType.CAT_AMBIENT),
            List.of(),
            List.of(SoundType.CAT_ATTACK),
            List.of(SoundType.CAT_HURT),
            List.of(SoundType.CAT_DEATH)
    )),

    DONKEY(new MobBehavior(
            List.of(SoundType.DONKEY_AMBIENT),
            List.of(),
            List.of(SoundType.DONKEY_ATTACK),
            List.of(SoundType.DONKEY_HURT),
            List.of(SoundType.DONKEY_DEATH)
    )),

    FOX(new MobBehavior(
            List.of(SoundType.FOX_AMBIENT),
            List.of(),
            List.of(SoundType.FOX_ATTACK),
            List.of(SoundType.FOX_HURT),
            List.of(SoundType.FOX_DEATH)
    )),

    FROG(new MobBehavior(
            List.of(SoundType.FROG_AMBIENT),
            List.of(),
            List.of(SoundType.FROG_ATTACK),
            List.of(SoundType.FROG_HURT),
            List.of(SoundType.FROG_DEATH)
    )),

    GOAT(new MobBehavior(
            List.of(SoundType.GOAT_AMBIENT),
            List.of(),
            List.of(SoundType.GOAT_ATTACK),
            List.of(SoundType.GOAT_HURT),
            List.of(SoundType.GOAT_DEATH)
    )),

    HOGLIN(new MobBehavior(
            List.of(SoundType.HOGLIN_AMBIENT),
            List.of(),
            List.of(SoundType.HOGLIN_ATTACK),
            List.of(SoundType.HOGLIN_HURT),
            List.of(SoundType.HOGLIN_DEATH)
    )),

    HUSK(new MobBehavior(
            List.of(SoundType.HUSK_AMBIENT),
            List.of(),
            List.of(SoundType.HUSK_ATTACK),
            List.of(SoundType.HUSK_HURT),
            List.of(SoundType.HUSK_DEATH)
    )),

    LLAMA(new MobBehavior(
            List.of(SoundType.LLAMA_AMBIENT),
            List.of(),
            List.of(SoundType.LLAMA_ATTACK),
            List.of(SoundType.LLAMA_HURT),
            List.of(SoundType.LLAMA_DEATH)
    )),

    PANDA(new MobBehavior(
            List.of(SoundType.PANDA_AMBIENT),
            List.of(),
            List.of(SoundType.PANDA_ATTACK),
            List.of(SoundType.PANDA_HURT),
            List.of(SoundType.PANDA_DEATH)
    )),

    PIG(new MobBehavior(
            List.of(SoundType.PIG_AMBIENT),
            List.of(),
            List.of(SoundType.PIG_ATTACK),
            List.of(SoundType.PIG_HURT),
            List.of(SoundType.PIG_DEATH)
    )),

    POLAR_BEAR(new MobBehavior(
            List.of(SoundType.POLAR_BEAR_AMBIENT),
            List.of(),
            List.of(SoundType.POLAR_BEAR_ATTACK),
            List.of(SoundType.POLAR_BEAR_HURT),
            List.of(SoundType.POLAR_BEAR_DEATH)
    )),

    RABBIT(new MobBehavior(
            List.of(SoundType.RABBIT_AMBIENT),
            List.of(),
            List.of(SoundType.RABBIT_ATTACK),
            List.of(SoundType.RABBIT_HURT),
            List.of(SoundType.RABBIT_DEATH)
    )),

    RAVAGER(new MobBehavior(
            List.of(SoundType.RAVAGER_AMBIENT),
            List.of(),
            List.of(SoundType.RAVAGER_ATTACK),
            List.of(SoundType.RAVAGER_HURT),
            List.of(SoundType.RAVAGER_DEATH)
    )),

    SNIFFER(new MobBehavior(
            List.of(SoundType.SNIFFER_AMBIENT),
            List.of(),
            List.of(SoundType.SNIFFER_ATTACK),
            List.of(SoundType.SNIFFER_HURT),
            List.of(SoundType.SNIFFER_DEATH)
    )),

    SPIDER(new MobBehavior(
            List.of(SoundType.SPIDER_AMBIENT),
            List.of(),
            List.of(SoundType.SPIDER_ATTACK),
            List.of(SoundType.SPIDER_HURT),
            List.of(SoundType.SPIDER_DEATH)
    )),

    STRIDER(new MobBehavior(
            List.of(SoundType.STRIDER_AMBIENT),
            List.of(),
            List.of(SoundType.STRIDER_ATTACK),
            List.of(SoundType.STRIDER_HURT),
            List.of(SoundType.STRIDER_DEATH)
    )),

    TURTLE(new MobBehavior(
            List.of(SoundType.TURTLE_AMBIENT),
            List.of(),
            List.of(SoundType.TURTLE_ATTACK),
            List.of(SoundType.TURTLE_HURT),
            List.of(SoundType.TURTLE_DEATH)
    )),

    WITCH(new MobBehavior(
            List.of(SoundType.WITCH_AMBIENT),
            List.of(),
            List.of(SoundType.WITCH_ATTACK),
            List.of(SoundType.WITCH_HURT),
            List.of(SoundType.WITCH_DEATH)
    ));


    private final MobBehavior behavior;

    MobBehaviors(MobBehavior behavior) {
        this.behavior = behavior;
    }

    public MobBehavior getBehavior() {
        return behavior;
    }
}
