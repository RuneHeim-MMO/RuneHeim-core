package org.zuma.examplePlugin.domain.recipe.constants;

import org.zuma.examplePlugin.core.constants.SoundType;

public enum RecipeType {
    INVENTORY_RECIPE("Craft", "Crafting", "Crafted", SoundType.CRAFT_START, SoundType.CRAFT_STEP, SoundType.CRAFT_SUCCESS, true),
    CRAFTING_TABLE_RECIPE("Craft", "Crafting", "Crafted", SoundType.CRAFT_START, SoundType.CRAFT_STEP, SoundType.CRAFT_SUCCESS, true),
    FLETCHING_TABLE_RECIPE("Craft", "Crafting", "Crafted", SoundType.CRAFT_START, SoundType.CRAFT_STEP, SoundType.CRAFT_SUCCESS, true),
    SMELTING_RECIPE("Smelt", "Smelting", "Smelted", SoundType.SMELT_START, SoundType.SMELT_STEP, SoundType.SMELT_SUCCESS, true),
    SMITHING_RECIPE("Smith", "Smithing", "Smithed", SoundType.SMITHING_START, SoundType.SMITHING_STEP, SoundType.SMITHING_SUCCESS, true),
    COOKING_RECIPE("Prepare", "Preparing", "Prepared", SoundType.COOKING_START, SoundType.COOKING_STEP, SoundType.COOKING_SUCCESS, true),
    CAMPFIRE_RECIPE("Start", "Starting", "Started", SoundType.CAMPFIRE_RECIPE_START, SoundType.CAMPFIRE_RECIPE_STEP, SoundType.CAMPFIRE_RECIPE_START, false),
    SPINNING_WHEEL_RECIPE("Spin", "Spinning", "Spun", SoundType.SPINNING_WHEEL_RECIPE_START, SoundType.SPINNING_WHEEL_RECIPE_STEP, SoundType.SPINNING_WHEEL_RECIPE_SUCCESS, true),
    TANNER_RECIPE("Tan", "Tanning", "Tanned", SoundType.TANNER_RECIPE_START, SoundType.TANNER_RECIPE_STEP, SoundType.TANNER_RECIPE_SUCCESS, true);

    private final String verb;
    private final String continuousVerb;
    private final String pastTenseVerb;
    private final SoundType startSound, stepSound, finishSound;
    private final boolean stackable;

    RecipeType(String verb, String continuousVerb, String pastTenseVerb, SoundType startSound, SoundType stepSound, SoundType finishSound, boolean stackable) {
        this.verb = verb;
        this.continuousVerb = continuousVerb;
        this.pastTenseVerb = pastTenseVerb;
        this.stepSound = stepSound;
        this.finishSound = finishSound;
        this.startSound = startSound;
        this.stackable = stackable;
    }

    public String getVerb() {
        return verb;
    }

    public String getContinuousVerb() {
        return continuousVerb;
    }

    public String getPastTenseVerb() {
        return pastTenseVerb;
    }

    public SoundType getStepSound() {
        return stepSound;
    }

    public SoundType getFinishSound() {
        return finishSound;
    }

    public SoundType getStartSound() {
        return startSound;
    }

    public boolean isStackable() {
        return stackable;
    }
}
