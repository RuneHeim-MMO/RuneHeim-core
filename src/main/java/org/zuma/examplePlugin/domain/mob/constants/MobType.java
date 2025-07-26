package org.zuma.examplePlugin.domain.mob.constants;

public enum MobType {
    AGGRESSIVE_BANDIT_1("aggressive_bandit_1"),
    AGGRESSIVE_BANDIT_2("aggressive_bandit_2"),

    GOBLIN_LOW_LVL_1("goblin_low_lvl_1"),
    GOBLIN_LOW_LVL_2("goblin_low_lvl_2"),
    GOBLIN_LOW_LVL_3("goblin_low_lvl_3"),
    GOBLIN_LOW_LVL_4("goblin_low_lvl_4"),
    GOBLIN_LOW_LVL_5("goblin_low_lvl_5"),


    GENERIC_SHOPKEEPER("generic_shopkeeper"),
    FUSION_MASTER("fusion_master"),
    SHEEP("sheep"), // Can shear wool off these

    // City guards (disguised iron golems to kill aggressive mobs near and inside of the cities)
    CITY_GUARD_STATIC("city_guard_static"),
    CITY_GUARD_WALKING("city_guard_walking"),

    // Boars
    BOAR_BROWN_ALPHA("yungwilder_isles_boar_brown_alpha"),
    BOAR_BROWN("yungwilder_isles_boar_brown"),
    BOAR_GRAY_ALPHA("yungwilder_isles_boar_gray_alpha"),
    BOAR_GRAY("yungwilder_isles_boar_gray"),
    BOAR_LIGHT_BROWN_ALPHA("yungwilder_isles_boar_light_brown_alpha"),
    BOAR_LIGHT_BROWN("yungwilder_isles_boar_light_brown"),

    // Wolves
    WOLF_BLACK_ALPHA("yungwilder_isles_wolf_black_alpha"),
    WOLF_BLACK("yungwilder_isles_wolf_black"),
    WOLF_BLONDE_ALPHA("yungwilder_isles_wolf_blonde_alpha"),
    WOLF_BLONDE("yungwilder_isles_wolf_blonde"),
    WOLF_GRAY_DARK_ALPHA("yungwilder_isles_wolf_gray_dark_alpha"),
    WOLF_GRAY_DARK("yungwilder_isles_wolf_gray_dark"),
    WOLF_GRAY_LIGHT_ALPHA("yungwilder_isles_wolf_gray_light_alpha"),
    WOLF_GRAY_LIGHT("yungwilder_isles_wolf_gray_light"),

    CHICKEN("chicken"),
    RAT("rat"),
    AXOLOTL("axolotl"),
    ARMADILLO("armadillo"),
    BEE("bee"),
    CAT("cat"),
    DONKEY("donkey"),
    FOX("fox"),
    FROG("frog"),
    GOAT("goat"),
    HOGLIN("hoglin"),
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
