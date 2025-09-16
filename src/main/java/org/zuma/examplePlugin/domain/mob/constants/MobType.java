package org.zuma.examplePlugin.domain.mob.constants;

public enum MobType {
    // Tutorial island
    PEASANT_MINER(),
    TUTOR_BLACKSMITH(),
    TUTOR_LUMBERJACK(),
    TUTOR_SURVIVAL(),

    COMBAT_DUMMY_FACE_SOUTH(),
    MOLE_BASE_30(),
    MOLE_BASE_50(),
    MOLE_BASE_70(),
    EAGLE_BLACKBASE_57(),
    EAGLE_BROWN_BASE_59(),
    EAGLE_BROWNHEAD_BASE_55(),

    BROWN_FARMHOUSE_TRADER(),
    ABRAHAM_FAIRY_TEACHER(),
    SHEPHERD(), // Sheep shearer quest

    COW_BASE_5(),
    CALF_BASE_3(),

    BUNNY_BLACK_BASE_12(),
    BUNNY_BROWN_BASE_14(),
    BUNNY_GRAY_BASE_15(),
    BUNNY_PET_BASE_12(),
    BUNNY_WHITE_BASE_15(),

    GOBLIN_1_BASE_1(),
    GOBLIN_2_BASE_1(),
    GOBLIN_3_BASE_1(),
    GOBLIN_4_BASE_1(),
    GOBLIN_5_BASE_1(),

    GOBLIN_1_BASE_4(),
    GOBLIN_2_BASE_4(),
    GOBLIN_3_BASE_4(),
    GOBLIN_4_BASE_4(),
    GOBLIN_5_BASE_4(),

    BANK_TELLER_1(),
    BANK_TELLER_2(),
    BANK_TELLER_3(),
    INVESTMENT_BANKER(),

    AUCTIONEER(),
    JEWLER(),
    DRAKKAR_BLACKSMITH(),
    GENERAL_STORE(),
    FUSION_MASTER(),
    SHEEP(), // Can shear wool off these

    // City guards (disguised iron golems to kill aggressive mobs near and inside of the cities)
    CITY_GUARD_STATIC(),
    CITY_GUARD_WALKING(),

    // Boars
    BOAR_BROWN_ALPHA_BASE10(),
    BOAR_BROWN_BASE_3(),
    BOAR_GRAY_ALPHA_BASE_12(),
    BOAR_GRAY_BASE_3(),
    BOAR_LIGHT_BROWN_ALPHA_BASE_14(),
    BOAR_LIGHT_BROWN_BASE_3(),
    WILD_PIG_BASE_48(),
    WILD_PIG_BABY_BASE_40(),
    WILD_PIG_BASE_45(),


    // Wolves
    WOLF_BLACK_ALPHA_BASE_36(),
    WOLF_BLACK_BASE_20(),
    WOLF_BLONDE_ALPHA_BASE_30(),
    WOLF_BLONDE_BASE_22(),
    WOLF_GRAY_DARK_ALPHA_BASE_32(),
    WOLF_GRAY_DARK_BASE_24(),
    WOLF_GRAY_LIGHT_ALPHA_BASE_34(),
    WOLF_GRAY_LIGHT_BASE_26(),

    // Deer
    DEER_BABY_BASE_15(),
    DEER_FEMALE_BASE_29(),
    DEER_BASE_34(),


    CHICKEN_BASE_2(),
    RAT(),
    AXOLOTL(),
    ARMADILLO_BASE_60(),
    ARMADILLO_NON_AGGRO_BASE_15(),
    BEE(),
    CAT(),
    DONKEY(),
    FOX(),
    FROG(),
    GOAT(),
    HOGLIN_BASE_5(),
    HUSK(),
    LLAMA(),
    PANDA(),
    PIG(),
    POLAR_BEAR(),
    RABBIT(),
    RAVAGER(),
    SNIFFER(),
    SPIDER(),
    STRIDER(),
    TURTLE(),
    WITCH();


    MobType() {
    }

    public String getId() {
        return this.toString();
    }
}
