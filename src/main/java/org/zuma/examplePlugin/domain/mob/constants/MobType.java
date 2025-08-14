package org.zuma.examplePlugin.domain.mob.constants;

public enum MobType {
    GOBLIN_1_BASE_1("goblin_low_lvl_1"),
    GOBLIN_2_BASE_1("goblin_low_lvl_2"),
    GOBLIN_3_BASE_1("goblin_low_lvl_3"),
    GOBLIN_4_BASE_1("goblin_low_lvl_4"),
    GOBLIN_5_BASE_1("goblin_low_lvl_5"),

    GOBLIN_1_BASE_4("goblin_low_lvl_1_base_4"),
    GOBLIN_2_BASE_4("goblin_low_lvl_2_base_4"),
    GOBLIN_3_BASE_4("goblin_low_lvl_3_base_4"),
    GOBLIN_4_BASE_4("goblin_low_lvl_4_base_4"),
    GOBLIN_5_BASE_4("goblin_low_lvl_5_base_4"),



    BANK_TELLER_1("bank_teller_1"),
    BANK_TELLER_2("bank_teller_2"),
    BANK_TELLER_3("bank_teller_3"),
    INVESTMENT_BANKER("investment_banker"),

    JEWLER("jeweler"),
    DRAKKAR_BLACKSMITH("drakkar_blacksmith"),
    GENERAL_STORE("generic_shopkeeper"),
    FUSION_MASTER("fusion_master"),
    SHEEP("sheep"), // Can shear wool off these

    // City guards (disguised iron golems to kill aggressive mobs near and inside of the cities)
    CITY_GUARD_STATIC("city_guard_static"),
    CITY_GUARD_WALKING("city_guard_walking"),

    // Boars
    BOAR_BROWN_ALPHA_BASE10("yungwilder_isles_boar_brown_alpha"),
    BOAR_BROWN_BASE_3("yungwilder_isles_boar_brown"),
    BOAR_GRAY_ALPHA_BASE_12("yungwilder_isles_boar_gray_alpha"),
    BOAR_GRAY_BASE_3("yungwilder_isles_boar_gray"),
    BOAR_LIGHT_BROWN_ALPHA_BASE_14("yungwilder_isles_boar_light_brown_alpha"),
    BOAR_LIGHT_BROWN_BASE_3("yungwilder_isles_boar_light_brown"),

    // Wolves
    WOLF_BLACK_ALPHA_BASE_36("yungwilder_isles_wolf_black_alpha"),
    WOLF_BLACK_BASE_20("yungwilder_isles_wolf_black"),
    WOLF_BLONDE_ALPHA_BASE_30("yungwilder_isles_wolf_blonde_alpha"),
    WOLF_BLONDE_BASE_22("yungwilder_isles_wolf_blonde"),
    WOLF_GRAY_DARK_ALPHA_BASE_32("yungwilder_isles_wolf_gray_dark_alpha"),
    WOLF_GRAY_DARK_BASE_24("yungwilder_isles_wolf_gray_dark"),
    WOLF_GRAY_LIGHT_ALPHA_BASE_34("yungwilder_isles_wolf_gray_light_alpha"),
    WOLF_GRAY_LIGHT_BASE_26("yungwilder_isles_wolf_gray_light"),

    CHICKEN_BASE_2("chicken"),
    RAT("rat"),
    AXOLOTL("axolotl"),
    ARMADILLO("armadillo"),
    BEE("bee"),
    CAT("cat"),
    DONKEY("donkey"),
    FOX("fox"),
    FROG("frog"),
    GOAT("goat"),
    HOGLIN_BASE_5("hoglin"),
    HUSK("husk"),
    LLAMA("llama"),
    PANDA("panda"),
    PIG("pig"),
    POLAR_BEAR("polar_bear"),
    RABBIT("rabbit"),
    RAVAGER("ravager"),
    SNIFFER("sniffer"),
    SPIDER("spider"),
    STRIDER("strider"),
    TURTLE("turtle"),
    WITCH("witch");


    private final String id;

    MobType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
