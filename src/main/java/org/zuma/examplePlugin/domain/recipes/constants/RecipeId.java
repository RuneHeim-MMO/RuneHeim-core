package org.zuma.examplePlugin.domain.recipes.constants;

public enum RecipeId {
    BRONZE_BAR(0),
    IRON_BAR(1),
    STEEL_BAR(2),
    MITHRIL_BAR(3),
    ADAMANTITE_BAR(4),
    RUNITE_BAR(5),

    COOKED_HERRING(100),
    COOKED_MINNOW(101),
    COOKED_TROUT(102),
    COOKED_SALMON(103),
    COOKED_TUNA(104),
    COOKED_CRAWLER(105),
    COOKED_PIKE(106),
    COOKED_EEL(107),
    COOKED_SHARK(108),
    COOKED_TURTLE(109),
    COOKED_RAY(110);

    private final int id;

    RecipeId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
