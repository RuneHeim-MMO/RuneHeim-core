package org.zuma.examplePlugin.domain.prayer;

import org.zuma.examplePlugin.domain.gui.constants.GUIControl;
import org.zuma.examplePlugin.domain.modifier.Modifier;
import org.zuma.examplePlugin.domain.modifier.constants.ModifierType;

import java.util.List;

public enum Prayer {
    STONEHIDE(0, "Stonehide", "Increases your defence by 5%", 1,
            List.of(new Modifier(ModifierType.DEFENCE_BOOST_PERCENT, 5)),
            List.of(), GUIControl.PRAYER_0_ACTIVE, GUIControl.PRAYER_0_INACTIVE, 0.1),

    SURGE_OF_POWER(1, "Surge of Power", "Increases your strength by 5%", 4,
            List.of(new Modifier(ModifierType.STRENGTH_BOOST_PERCENT, 5)),
            List.of(), GUIControl.PRAYER_1_ACTIVE, GUIControl.PRAYER_1_INACTIVE, 0.1),

    MINDS_FOCUS(2, "Mind’s Focus", "Increases your attack by 5%", 7,
            List.of(new Modifier(ModifierType.ATTACK_BOOST_PERCENT, 5)),
            List.of(), GUIControl.PRAYER_2_ACTIVE, GUIControl.PRAYER_2_INACTIVE, 0.1),

    KEEN_SIGHT(3, "Keen Sight", "Increases your ranged accuracy and strength by 5%", 8,
            List.of(new Modifier(ModifierType.RANGED_ACC_BOOST_PERCENT, 5), new Modifier(ModifierType.RANGED_STR_BOOST_PERCENT, 5)),
            List.of(), GUIControl.PRAYER_3_ACTIVE, GUIControl.PRAYER_3_INACTIVE, 0.1),

    GRANITE_GUARD(4, "Granite Guard", "Increases your defence by 10%", 10,
            List.of(new Modifier(ModifierType.DEFENCE_BOOST_PERCENT, 10)),
            List.of(STONEHIDE), GUIControl.PRAYER_4_ACTIVE, GUIControl.PRAYER_4_INACTIVE, 0.15),

    TITANS_MIGHT(5, "Titan’s Might", "Increases your strength by 10%", 14,
            List.of(new Modifier(ModifierType.STRENGTH_BOOST_PERCENT, 10)),
            List.of(SURGE_OF_POWER), GUIControl.PRAYER_5_ACTIVE, GUIControl.PRAYER_5_INACTIVE, 0.15),

    SWIFT_INSTINCT(6, "Swift Instinct", "Increases your attack by 10%", 18,
            List.of(new Modifier(ModifierType.ATTACK_BOOST_PERCENT, 10)),
            List.of(MINDS_FOCUS), GUIControl.PRAYER_6_ACTIVE, GUIControl.PRAYER_6_INACTIVE, 0.15),

    QUICK_RECOVERY(7, "Quick Recovery", "Doubles your natural hitpoint regeneration rate", 24,
            List.of(new Modifier(ModifierType.HP_RECOVERY_BOOST_PERCENT, 100)),
            List.of(), GUIControl.PRAYER_7_ACTIVE, GUIControl.PRAYER_7_INACTIVE, 0.15),

    FALCON_GAZE(8, "Falcon Gaze", "Increases your ranged accuracy and strength by 10%", 29,
            List.of(new Modifier(ModifierType.RANGED_ACC_BOOST_PERCENT, 10), new Modifier(ModifierType.RANGED_STR_BOOST_PERCENT, 10)),
            List.of(KEEN_SIGHT), GUIControl.PRAYER_8_ACTIVE, GUIControl.PRAYER_8_INACTIVE, 0.15),

    IRON_VEIL(9, "Iron Veil", "Increases your defence by 15%", 33,
            List.of(new Modifier(ModifierType.DEFENCE_BOOST_PERCENT, 15)),
            List.of(GRANITE_GUARD, STONEHIDE), GUIControl.PRAYER_9_ACTIVE, GUIControl.PRAYER_9_INACTIVE, 0.2),

    OVERWHELMING_FORCE(10, "Overwhelming Force", "Increases your strength by 15%", 37,
            List.of(new Modifier(ModifierType.STRENGTH_BOOST_PERCENT, 15)),
            List.of(TITANS_MIGHT, SURGE_OF_POWER), GUIControl.PRAYER_10_ACTIVE, GUIControl.PRAYER_10_INACTIVE, 0.2),

    LIGHTNING_REFLEX(11, "Lightning Reflex", "Increases your attack by 15%", 38,
            List.of(new Modifier(ModifierType.ATTACK_BOOST_PERCENT, 15)),
            List.of(SWIFT_INSTINCT, MINDS_FOCUS), GUIControl.PRAYER_11_ACTIVE, GUIControl.PRAYER_11_INACTIVE, 0.2),

    PREDATOR_VISION(12, "Predator Vision", "Increases your ranged accuracy and strength by 15%", 40,
            List.of(new Modifier(ModifierType.RANGED_ACC_BOOST_PERCENT, 15), new Modifier(ModifierType.RANGED_STR_BOOST_PERCENT, 15)),
            List.of(FALCON_GAZE, KEEN_SIGHT), GUIControl.PRAYER_12_ACTIVE, GUIControl.PRAYER_12_INACTIVE, 0.2);


    private final int id;
    private final String name;
    private final String description;
    private final int levelRequirement;
    private final double drainRatePerTick;
    private final List<Modifier> modifiers;
    private final List<Prayer> conflictingPrayers;
    private final GUIControl enabledBtn, disabledBtn;

    Prayer(int id, String name, String description, int levelRequirement, List<Modifier> modifiers, List<Prayer> conflictingPrayers, GUIControl enabledBtn, GUIControl disabledBtn, double drainRatePerTick) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.levelRequirement = levelRequirement;
        this.modifiers = modifiers;
        this.conflictingPrayers = conflictingPrayers;
        this.enabledBtn = enabledBtn;
        this.disabledBtn = disabledBtn;
        this.drainRatePerTick = drainRatePerTick;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public List<Modifier> getModifiers() {
        return modifiers;
    }

    public List<Prayer> getConflictingPrayers() {
        return conflictingPrayers;
    }

    public GUIControl getEnabledBtn() {
        return enabledBtn;
    }

    public GUIControl getDisabledBtn() {
        return disabledBtn;
    }

    public double getDrainRatePerTick() {
        return drainRatePerTick;
    }
}
