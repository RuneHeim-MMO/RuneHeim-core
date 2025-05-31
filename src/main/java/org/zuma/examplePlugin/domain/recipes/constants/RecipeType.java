package org.zuma.examplePlugin.domain.recipes.constants;

import org.zuma.examplePlugin.core.constants.SoundType;

public enum RecipeType {
    INVENTORY_RECIPE("Craft", "Crafting", "Crafted", SoundType.CRAFT_STEP, SoundType.MINING_SUCCESS),
    SMELTING_RECIPE("Smelt", "Smelting", "Smelted", SoundType.CRAFT_STEP, SoundType.MINING_SUCCESS),
    SMITHING_RECIPE("Smith", "Smithing", "Smithed", SoundType.CRAFT_STEP, SoundType.MINING_SUCCESS),
    COOKING_RECIPE("Cook", "Cooking", "Cooked", SoundType.CRAFT_STEP, SoundType.MINING_SUCCESS);

    private final String verb;
    private final String continuousVerb;
    private final String pastTenseVerb;
    private final SoundType stepSound;
    private final SoundType finishSound;

    // Constructor
    RecipeType(String verb, String continuousVerb, String pastTenseVerb, SoundType stepSound, SoundType finishSound) {
        this.verb = verb;
        this.continuousVerb = continuousVerb;
        this.pastTenseVerb = pastTenseVerb;
        this.stepSound = stepSound;
        this.finishSound = finishSound;
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
}
