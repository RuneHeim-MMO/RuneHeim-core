package org.zuma.examplePlugin.domain.cutscene;

import javax.xml.stream.Location;

public class CutsceneEntityInstance {
    private final Cutscene cutscene;
    private final CutsceneEntity base;

    public CutsceneEntityInstance(CutsceneEntity base, Cutscene cutscene) {
        this.base = base;
        this.cutscene = cutscene;
    }

    public void setVisibleToCameraOnly() {
        // TODO: implement
    }

    public void setLocation(Location location) {

    }


    public void moveTo(Location location) {
    }


    public void cleanup() {
    }

}
