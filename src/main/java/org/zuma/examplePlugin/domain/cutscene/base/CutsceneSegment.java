package org.zuma.examplePlugin.domain.cutscene.base;

import org.bukkit.Location;
import org.zuma.examplePlugin.domain.cutscene.Cutscene;
import org.zuma.examplePlugin.domain.cutscene.CutsceneCamera;

public abstract class CutsceneSegment {
    private final Location initialCameraLocation;
    private final int durationTicks;

    public CutsceneSegment(Location initialCameraLocation, int durationTicks) {
        this.initialCameraLocation = initialCameraLocation;
        this.durationTicks = durationTicks;
    }

    public int getDurationTicks() {
        return durationTicks;
    }

    public void playSegment(CutsceneCamera camera, Cutscene cutscene) {
        camera.setLocation(this.initialCameraLocation);
    }

    public abstract void tick(double progress, Cutscene cutscene);

    public abstract void cleanup();
}
