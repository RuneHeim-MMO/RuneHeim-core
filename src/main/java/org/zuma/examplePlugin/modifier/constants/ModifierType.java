package org.zuma.examplePlugin.modifier.constants;

public enum ModifierType {
    MINING_BOOST("Mining Boost");

    private final String label;

    ModifierType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }


}
