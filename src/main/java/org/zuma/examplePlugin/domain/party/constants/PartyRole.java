package org.zuma.examplePlugin.domain.party.constants;

public enum PartyRole {
    OWNER("Owner"),
    MEMBER("Member");

    private final String label;

    PartyRole(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
