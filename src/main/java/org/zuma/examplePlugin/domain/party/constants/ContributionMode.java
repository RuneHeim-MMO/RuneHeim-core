package org.zuma.examplePlugin.domain.party.constants;

import org.zuma.examplePlugin.domain.gui.constants.GUIControl;

public enum ContributionMode {
    PERCENT_10(10, GUIControl.PARTY_10_CONTRIBUTION),
    PERCENT_20(20, GUIControl.PARTY_20_CONTRIBUTION),
    PERCENT_30(30, GUIControl.PARTY_30_CONTRIBUTION);

    private final int percent;
    private final GUIControl guiControl;

    ContributionMode(int percent, GUIControl guiControl) {
        this.percent = percent;
        this.guiControl = guiControl;
    }

    public int getPercent() {
        return percent;
    }

    public GUIControl getGuiControl() {
        return guiControl;
    }
}
