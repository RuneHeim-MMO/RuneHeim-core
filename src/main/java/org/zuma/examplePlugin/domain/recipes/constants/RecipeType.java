package org.zuma.examplePlugin.domain.recipes.constants;

import org.zuma.examplePlugin.core.constants.SoundType;

public enum RecipeType {
    INVENTORY_RECIPE("Craft", "Crafting", "Crafted", SoundType.CRAFT_START, SoundType.CRAFT_STEP, SoundType.CRAFT_SUCCESS),
    SMELTING_RECIPE("Smelt", "Smelting", "Smelted", SoundType.SMELT_START, SoundType.SMELT_STEP, SoundType.SMELT_SUCCESS),
    SMITHING_RECIPE("Smith", "Smithing", "Smithed", SoundType.SMITHING_START, SoundType.SMITHING_STEP, SoundType.SMITHING_SUCCESS),
    COOKING_RECIPE("Cook", "Cooking", "Cooked", SoundType.COOKING_START, SoundType.COOKING_STEP, SoundType.COOKING_SUCCESS),
    CAMPFIRE_RECIPE("Start", "Starting", "Started", SoundType.CAMPFIRE_RECIPE_START, SoundType.CAMPFIRE_RECIPE_STEP, SoundType.CAMPFIRE_RECIPE_START);

    private final String verb;
    private final String continuousVerb;
    private final String pastTenseVerb;
    private final SoundType startSound, stepSound, finishSound;

    RecipeType(String verb, String continuousVerb, String pastTenseVerb, SoundType startSound, SoundType stepSound, SoundType finishSound) {
        this.verb = verb;
        this.continuousVerb = continuousVerb;
        this.pastTenseVerb = pastTenseVerb;
        this.stepSound = stepSound;
        this.finishSound = finishSound;
        this.startSound = startSound;
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
}
