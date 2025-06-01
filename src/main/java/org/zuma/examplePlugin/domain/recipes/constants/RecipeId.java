package org.zuma.examplePlugin.domain.recipes.constants;

public enum RecipeId {
    BRONZE_BAR(0),
    IRON_BAR(1),
    STEEL_BAR(2),
    MITHRIL_BAR(3),
    ADAMANTITE_BAR(4),
    RUNITE_BAR(5);

    private final int id;

    RecipeId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
