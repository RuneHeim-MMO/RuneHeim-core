package org.zuma.examplePlugin.domain.mob.behavior;

import org.zuma.examplePlugin.core.constants.SoundType;

import java.util.List;
import java.util.function.Function;

public enum MobBehaviors {

    LEOPARD((pitchModifier) -> new MobBehavior(
            pitchModifier,
            List.of(SoundType.LEOPARD_AMBIENT),
            List.of(SoundType.LEOPARD_ATTACK, SoundType.LEOPARD_GROWL, SoundType.LEOPARD_GROWL_2),
            List.of(SoundType.LEOPARD_ATTACK, SoundType.LEOPARD_GROWL, SoundType.LEOPARD_GROWL_2),
            List.of(SoundType.LEOPARD_HURT),
            List.of(SoundType.LEOPARD_DEATH)
    )),

    CENTIPEDE((pitchModifier) -> new MobBehavior(
            pitchModifier,
            List.of(SoundType.CENTIPEDE_AMBIENT),
            List.of(SoundType.CENTIPEDE_ATTACK),
            List.of(SoundType.CENTIPEDE_CALL),
            List.of(SoundType.CENTIPEDE_HURT),
            List.of(SoundType.CENTIPEDE_DEATH)
    )),

    ANT((pitchModifier) -> new MobBehavior(
            pitchModifier,
            List.of(SoundType.ANT_AMBIENT),
            List.of(SoundType.ANT_ATTACK),
            List.of(SoundType.ANT_CALL),
            List.of(SoundType.ANT_HURT),
            List.of(SoundType.ANT_DEATH)
    )),

    COCKROACH((pitchModifier) -> new MobBehavior(
            pitchModifier,
            List.of(SoundType.COCKROACH_AMBIENT),
            List.of(SoundType.COCKROACH_ATTACK),
            List.of(SoundType.COCKROACH_CALL),
            List.of(SoundType.COCKROACH_HURT),
            List.of(SoundType.COCKROACH_DEATH)
    )),

    CRICKET((pitchModifier) -> new MobBehavior(
            pitchModifier,
            List.of(SoundType.CRICKET_AMBIENT),
            List.of(SoundType.CRICKET_ATTACK),
            List.of(SoundType.CRICKET_CALL),
            List.of(SoundType.CRICKET_HURT),
            List.of(SoundType.CRICKET_DEATH)
    )),

    MANTIS((pitchModifier) -> new MobBehavior(
            pitchModifier,
            List.of(SoundType.MANTIS_AMBIENT),
            List.of(SoundType.MANTIS_ATTACK),
            List.of(SoundType.MANTIS_CALL),
            List.of(SoundType.MANTIS_HURT),
            List.of(SoundType.MANTIS_DEATH)
    )),

    SCORPION((pitchModifier) -> new MobBehavior(
            pitchModifier,
            List.of(SoundType.SCORPION_AMBIENT),
            List.of(SoundType.SCORPION_ATTACK),
            List.of(SoundType.SCORPION_CALL),
            List.of(SoundType.SCORPION_HURT),
            List.of(SoundType.SCORPION_DEATH)
    )),


    PENGUIN((pitchModifier) -> new MobBehavior(
            pitchModifier,
            List.of(SoundType.PENGUIN_AMBIENT),
            List.of(SoundType.PENGUIN_ATTACK, SoundType.PENGUIN_CALL),
            List.of(SoundType.PENGUIN_ATTACK, SoundType.PENGUIN_CALL),
            List.of(SoundType.PENGUIN_HURT),
            List.of(SoundType.PENGUIN_DEATH)
    )),


    HELLFLESH_1((pitchModifier) -> new MobBehavior(
            pitchModifier,
            List.of(SoundType.HELLFLESH_AMBIENT),
            List.of(SoundType.HELLFLESH_CELEBRATE, SoundType.HELLFLESH_LAUGH),
            List.of(),
            List.of(SoundType.HELLFLESH_HURT),
            List.of(SoundType.HELLFLESH_DEATH)
    )),


    VILLAGER_MAN_1((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.VILLAGER_MAN_AMBIENT_1),
            List.of(SoundType.VILLAGER_MAN_CELEBRATE_1),
            List.of(SoundType.VILLAGER_MAN_NO_1),
            List.of(SoundType.VILLAGER_MAN_HURT_1),
            List.of(SoundType.VILLAGER_MAN_DEATH_1)
    )),

    VILLAGER_MAN_2((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.VILLAGER_MAN_AMBIENT_2),
            List.of(SoundType.VILLAGER_MAN_CELEBRATE_2),
            List.of(SoundType.VILLAGER_MAN_NO_2),
            List.of(SoundType.VILLAGER_MAN_HURT_2),
            List.of(SoundType.VILLAGER_MAN_DEATH_2)
    )),

    VILLAGER_MAN_3((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.VILLAGER_MAN_AMBIENT_3),
            List.of(SoundType.VILLAGER_MAN_CELEBRATE_3),
            List.of(SoundType.VILLAGER_MAN_NO_3),
            List.of(SoundType.VILLAGER_MAN_HURT_3),
            List.of(SoundType.VILLAGER_MAN_DEATH_3)
    )),

    VILLAGER_MAN_4((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.VILLAGER_MAN_AMBIENT_4),
            List.of(SoundType.VILLAGER_MAN_CELEBRATE_4),
            List.of(SoundType.VILLAGER_MAN_NO_4),
            List.of(SoundType.VILLAGER_MAN_HURT_4),
            List.of(SoundType.VILLAGER_MAN_DEATH_4)
    )),

    VILLAGER_MAN_5((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.VILLAGER_MAN_AMBIENT_5),
            List.of(SoundType.VILLAGER_MAN_CELEBRATE_5),
            List.of(SoundType.VILLAGER_MAN_NO_5),
            List.of(SoundType.VILLAGER_MAN_HURT_5),
            List.of(SoundType.VILLAGER_MAN_DEATH_5)
    )),

    MORVANE((pitchModifier) -> new MobBehavior(
            pitchModifier,
            List.of(SoundType.MORVANE_AMBIENT),
            List.of(SoundType.MORVANE_CELEBRATE),
            List.of(SoundType.MORVANE_ATTACK, SoundType.MORVANE_CALL),
            List.of(SoundType.MORVANE_HURT),
            List.of(SoundType.MORVANE_DEATH)
    )),

    // WOMAN variations
    VILLAGER_WOMAN_1((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.VILLAGER_WOMAN_AMBIENT_1),
            List.of(SoundType.VILLAGER_WOMAN_CELEBRATE_1),
            List.of(SoundType.VILLAGER_WOMAN_NO_1),
            List.of(SoundType.VILLAGER_WOMAN_HURT_1),
            List.of(SoundType.VILLAGER_WOMAN_DEATH_1)
    )),

    VILLAGER_WOMAN_2((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.VILLAGER_WOMAN_AMBIENT_2),
            List.of(SoundType.VILLAGER_WOMAN_CELEBRATE_2),
            List.of(SoundType.VILLAGER_WOMAN_NO_2),
            List.of(SoundType.VILLAGER_WOMAN_HURT_2),
            List.of(SoundType.VILLAGER_WOMAN_DEATH_2)
    )),

    VILLAGER_WOMAN_3((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.VILLAGER_WOMAN_AMBIENT_3),
            List.of(SoundType.VILLAGER_WOMAN_CELEBRATE_3),
            List.of(SoundType.VILLAGER_WOMAN_NO_3),
            List.of(SoundType.VILLAGER_WOMAN_HURT_3),
            List.of(SoundType.VILLAGER_WOMAN_DEATH_3)
    )),

    VILLAGER_WOMAN_4((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.VILLAGER_WOMAN_AMBIENT_4),
            List.of(SoundType.VILLAGER_WOMAN_CELEBRATE_4),
            List.of(SoundType.VILLAGER_WOMAN_NO_4),
            List.of(SoundType.VILLAGER_WOMAN_HURT_4),
            List.of(SoundType.VILLAGER_WOMAN_DEATH_4)
    )),

    VILLAGER_WOMAN_5((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.VILLAGER_WOMAN_AMBIENT_5),
            List.of(SoundType.VILLAGER_WOMAN_NO_5),
            List.of(SoundType.VILLAGER_WOMAN_NO_5),
            List.of(SoundType.VILLAGER_WOMAN_HURT_5),
            List.of(SoundType.VILLAGER_WOMAN_DEATH_5)
    )),

    FUSION_MASTER((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.FUSION_MASTER_AMBIENT_1, SoundType.FUSION_MASTER_AMBIENT_2, SoundType.FUSION_MASTER_AMBIENT_3),
            List.of(SoundType.FUSION_MASTER_SUCCESS),
            List.of(SoundType.FUSION_MASTER_NO),
            null,
            null
    )),

    GOBLIN((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.GOBLIN_AMBIENT),
            List.of(SoundType.GOBLIN_CELEBRATE),
            List.of(SoundType.GOBLIN_ATTACK),
            List.of(SoundType.GOBLIN_HURT),
            List.of(SoundType.GOBLIN_DEATH)
    )),

    SHEEP((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.SHEEP_AMBIENT),
            List.of(SoundType.SHEEP_SUCCESS),
            List.of(SoundType.SHEEP_NO),
            List.of(SoundType.SHEEP_HURT),
            List.of(SoundType.SHEEP_DEATH)
    )),

    BOAR((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.BOAR_AMBIENT_1, SoundType.BOAR_AMBIENT_2),
            List.of(),
            List.of(SoundType.BOAR_ATTACK_1, SoundType.BOAR_ATTACK_2),
            List.of(SoundType.BOAR_HURT_1, SoundType.BOAR_HURT_2),
            List.of(SoundType.BOAR_DEATH_1, SoundType.BOAR_DEATH_2)
    )),

    EAGLE((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.EAGLE_AMBIENT_1),
            List.of(),
            List.of(SoundType.EAGLE_ATTACK_1),
            List.of(SoundType.EAGLE_HURT_1),
            List.of(SoundType.EAGLE_DEATH_1)
    )),

    COW((pitchModifier) -> new MobBehavior(
            pitchModifier,
            List.of(SoundType.COW_AMBIENT_1),
            List.of(),
            List.of(SoundType.COW_ATTACK_1),
            List.of(SoundType.COW_HURT_1),
            List.of(SoundType.COW_DEATH_1)
    )),


    MOLE((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.MOLE_AMBIENT_1),
            List.of(),
            List.of(SoundType.MOLE_ATTACK_1),
            List.of(SoundType.MOLE_HURT_1),
            List.of(SoundType.MOLE_DEATH_1)
    )),


    WILD_PIG((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.WILD_PIG_AMBIENT_1, SoundType.WILD_PIG_AMBIENT_2),
            List.of(),
            List.of(SoundType.WILD_PIG_ATTACK_1, SoundType.WILD_PIG_ATTACK_2),
            List.of(SoundType.WILD_PIG_HURT_1, SoundType.WILD_PIG_HURT_2),
            List.of(SoundType.WILD_PIG_DEATH_1, SoundType.WILD_PIG_DEATH_2)
    )),

    WOLF((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.WOLF_AMBIENT_1, SoundType.WOLF_AMBIENT_2),
            List.of(),
            List.of(SoundType.WOLF_ATTACK_1, SoundType.WOLF_ATTACK_2),
            List.of(SoundType.WOLF_HURT_1, SoundType.WOLF_HURT_2),
            List.of(SoundType.WOLF_DEATH_1, SoundType.WOLF_DEATH_2)
    )),

    DEER((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.DEER_AMBIENT_1, SoundType.DEER_AMBIENT_2),
            List.of(),
            List.of(SoundType.DEER_ATTACK_1, SoundType.DEER_ATTACK_2),
            List.of(SoundType.DEER_HURT_1, SoundType.DEER_HURT_2),
            List.of(SoundType.DEER_DEATH_1, SoundType.DEER_DEATH_2)
    )),

    OCELOT((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.OCELOT_AMBIENT_1),
            List.of(),
            List.of(SoundType.OCELOT_ATTACK_1),
            List.of(SoundType.OCELOT_HURT_1),
            List.of(SoundType.OCELOT_DEATH_1)
    )),

    DEER_BABY((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.DEER_AMBIENT_1, SoundType.DEER_AMBIENT_2),
            List.of(),
            List.of(SoundType.DEER_ATTACK_1, SoundType.DEER_ATTACK_2),
            List.of(SoundType.DEER_HURT_1, SoundType.DEER_HURT_2),
            List.of(SoundType.DEER_DEATH_1, SoundType.DEER_DEATH_2)
    )),


    CHICKEN((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.CHICKEN_AMBIENT),
            List.of(),
            List.of(SoundType.CHICKEN_ATTACK),
            List.of(SoundType.CHICKEN_HURT),
            List.of(SoundType.CHICKEN_DEATH)
    )),

    RAT((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.RAT_AMBIENT),
            List.of(),
            List.of(SoundType.RAT_ATTACK),
            List.of(SoundType.RAT_HURT),
            List.of(SoundType.RAT_DEATH)
    )),

    AXOLOTL((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.AXOLOTL_AMBIENT),
            List.of(),
            List.of(SoundType.AXOLOTL_ATTACK),
            List.of(SoundType.AXOLOTL_HURT),
            List.of(SoundType.AXOLOTL_DEATH)
    )),

    ARMADILLO((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.ARMADILLO_AMBIENT),
            List.of(),
            List.of(SoundType.ARMADILLO_ATTACK),
            List.of(SoundType.ARMADILLO_HURT),
            List.of(SoundType.ARMADILLO_DEATH)
    )),

    BLAZE((pitchModifier) -> new MobBehavior(
            pitchModifier,
            List.of(SoundType.BLAZE_AMBIENT),
            List.of(SoundType.BLAZE_BURN),
            List.of(SoundType.BLAZE_ATTACK),
            List.of(SoundType.BLAZE_HURT),
            List.of(SoundType.BLAZE_DEATH)
    )),

    BEE((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.BEE_AMBIENT),
            List.of(),
            List.of(SoundType.BEE_ATTACK),
            List.of(SoundType.BEE_HURT),
            List.of(SoundType.BEE_DEATH)
    )),

    CAT((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.CAT_AMBIENT),
            List.of(),
            List.of(SoundType.CAT_ATTACK),
            List.of(SoundType.CAT_HURT),
            List.of(SoundType.CAT_DEATH)
    )),

    DONKEY((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.DONKEY_AMBIENT),
            List.of(),
            List.of(SoundType.DONKEY_ATTACK),
            List.of(SoundType.DONKEY_HURT),
            List.of(SoundType.DONKEY_DEATH)
    )),

    FOX((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.FOX_AMBIENT),
            List.of(),
            List.of(SoundType.FOX_ATTACK),
            List.of(SoundType.FOX_HURT),
            List.of(SoundType.FOX_DEATH)
    )),

    FROG((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.FROG_AMBIENT),
            List.of(),
            List.of(SoundType.FROG_ATTACK),
            List.of(SoundType.FROG_HURT),
            List.of(SoundType.FROG_DEATH)
    )),

    GOAT((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.GOAT_AMBIENT),
            List.of(),
            List.of(SoundType.GOAT_ATTACK),
            List.of(SoundType.GOAT_HURT),
            List.of(SoundType.GOAT_DEATH)
    )),

    HOGLIN((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.HOGLIN_AMBIENT),
            List.of(),
            List.of(SoundType.HOGLIN_ATTACK),
            List.of(SoundType.HOGLIN_HURT),
            List.of(SoundType.HOGLIN_DEATH)
    )),

    HUSK((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.HUSK_AMBIENT),
            List.of(),
            List.of(SoundType.HUSK_ATTACK),
            List.of(SoundType.HUSK_HURT),
            List.of(SoundType.HUSK_DEATH)
    )),

    LLAMA((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.LLAMA_AMBIENT),
            List.of(),
            List.of(SoundType.LLAMA_ATTACK),
            List.of(SoundType.LLAMA_HURT),
            List.of(SoundType.LLAMA_DEATH)
    )),

    PANDA((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.PANDA_AMBIENT),
            List.of(),
            List.of(SoundType.PANDA_ATTACK),
            List.of(SoundType.PANDA_HURT),
            List.of(SoundType.PANDA_DEATH)
    )),

    PIG((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.PIG_AMBIENT),
            List.of(),
            List.of(SoundType.PIG_ATTACK),
            List.of(SoundType.PIG_HURT),
            List.of(SoundType.PIG_DEATH)
    )),

    POLAR_BEAR((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.POLAR_BEAR_AMBIENT),
            List.of(),
            List.of(SoundType.POLAR_BEAR_ATTACK),
            List.of(SoundType.POLAR_BEAR_HURT),
            List.of(SoundType.POLAR_BEAR_DEATH)
    )),

    RABBIT((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.RABBIT_AMBIENT),
            List.of(),
            List.of(SoundType.RABBIT_ATTACK),
            List.of(SoundType.RABBIT_HURT),
            List.of(SoundType.RABBIT_DEATH)
    )),

    RAVAGER((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.RAVAGER_AMBIENT),
            List.of(),
            List.of(SoundType.RAVAGER_ATTACK),
            List.of(SoundType.RAVAGER_HURT),
            List.of(SoundType.RAVAGER_DEATH)
    )),

    SNIFFER((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.SNIFFER_AMBIENT),
            List.of(),
            List.of(SoundType.SNIFFER_ATTACK),
            List.of(SoundType.SNIFFER_HURT),
            List.of(SoundType.SNIFFER_DEATH)
    )),

    SPIDER((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.SPIDER_AMBIENT),
            List.of(),
            List.of(SoundType.SPIDER_ATTACK),
            List.of(SoundType.SPIDER_HURT),
            List.of(SoundType.SPIDER_DEATH)
    )),

    STRIDER((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.STRIDER_AMBIENT),
            List.of(),
            List.of(SoundType.STRIDER_ATTACK),
            List.of(SoundType.STRIDER_HURT),
            List.of(SoundType.STRIDER_DEATH)
    )),

    TURTLE((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.TURTLE_AMBIENT),
            List.of(),
            List.of(SoundType.TURTLE_ATTACK),
            List.of(SoundType.TURTLE_HURT),
            List.of(SoundType.TURTLE_DEATH)
    )),

    WITCH((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.WITCH_AMBIENT),
            List.of(),
            List.of(SoundType.WITCH_ATTACK),
            List.of(SoundType.WITCH_HURT),
            List.of(SoundType.WITCH_DEATH)
    )),


    BANDIT((pitchModifier) -> new MobBehavior(
            pitchModifier, List.of(SoundType.BANDIT_AMBIENT_1),
            List.of(SoundType.BANDIT_YES_1),
            List.of(SoundType.BANDIT_NO_1),
            List.of(SoundType.BANDIT_HURT_1),
            List.of(SoundType.BANDIT_DEATH_1)
    )),

    GHOST((pitchModifier) -> new MobBehavior(
            pitchModifier,
            List.of(
                    SoundType.GHOST_AMBIENT_1,
                    SoundType.GHOST_AMBIENT_2
            ),
            List.of(
                    SoundType.GHOST_CELEBRATE_1
            ),
            List.of(
                    SoundType.GHOST_NO_1
            ),
            List.of(
                    SoundType.GHOST_HURT_1,
                    SoundType.GHOST_HURT_2
            ),
            List.of(
                    SoundType.GHOST_DEATH_1
            )
    ));


    private final Function<Float, MobBehavior> behavior;

    MobBehaviors(Function<Float, MobBehavior> behavior) {
        this.behavior = behavior;
    }

    public MobBehavior getBehavior() {
        return behavior.apply(0F);
    }

    public MobBehavior getBehavior(float pitchModifier) {
        return behavior.apply(pitchModifier);
    }
}
