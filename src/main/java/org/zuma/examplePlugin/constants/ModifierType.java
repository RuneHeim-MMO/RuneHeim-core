package org.zuma.examplePlugin.constants;

public enum ModifierType {
    STRENGTH("Strength");

    private final String label;

    ModifierType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }


}
