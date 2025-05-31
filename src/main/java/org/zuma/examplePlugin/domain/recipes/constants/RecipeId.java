package org.zuma.examplePlugin.domain.recipes.constants;

public enum RecipeId {
    TEST_RECIPE(0);

    private final int id;

    RecipeId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
