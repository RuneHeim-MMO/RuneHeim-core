package org.zuma.examplePlugin.domain.map;

public enum MarkerType {
    FISHING("fishing-markers", "Fishing spots", "https://i.imgur.com/25Gbfy7.png"),
    TREE("tree-markers", "Trees", "https://i.imgur.com/y7oRDtE.png"),
    BIRCH("tree-birch-markers", "Birch trees", "https://i.imgur.com/NqA7YIZ.png"),
    MINING("mining-markers", "Ore veins", "https://i.imgur.com/E9sCMBZ.png"),
    BANKS("bank-markers", "Banks", "https://i.imgur.com/4vif6Tm.png"),
    SHOPS("shop-markers", "Shops", "https://i.imgur.com/D8qq3aO.png");

    private final String id, label, iconURL;

    MarkerType(String id, String label, String iconURL) {
        this.id = id;
        this.label = label;
        this.iconURL = iconURL;
    }

    public String getLabel() {
        return label;
    }

    public String getId() {
        return id;
    }

    public String getIconURL() {
        return iconURL;
    }
}
