package org.zuma.examplePlugin.domain.cutscene.impl;

import org.bukkit.Location;
import org.zuma.examplePlugin.domain.cutscene.Cutscene;
import org.zuma.examplePlugin.domain.cutscene.base.CutsceneSegment;

public class BezierCameraPathCutscene extends CutsceneSegment {
    private final Location startPoint;
    private final Location controlPoint1;
    private final Location controlPoint2;
    private final Location endPoint;

    // Camera bezier points (optional, can be null to use player points)
    private final Location cameraStartPoint;
    private final Location cameraControlPoint1;
    private final Location cameraControlPoint2;
    private final Location cameraEndPoint;

    public BezierCameraPathCutscene(
            Location startPoint, Location controlPoint1, Location controlPoint2, Location endPoint,
            int durationTicks
    ) {
        this(startPoint, controlPoint1, controlPoint2, endPoint, null, null, null, null, durationTicks);
    }

    public BezierCameraPathCutscene(
            Location startPoint, Location controlPoint1, Location controlPoint2, Location endPoint,
            Location cameraStartPoint, Location cameraControlPoint1, Location cameraControlPoint2, Location cameraEndPoint,
            int durationTicks
    ) {
        super(startPoint, durationTicks);
        this.startPoint = startPoint;
        this.controlPoint1 = controlPoint1;
        this.controlPoint2 = controlPoint2;
        this.endPoint = endPoint;
        this.cameraStartPoint = cameraStartPoint;
        this.cameraControlPoint1 = cameraControlPoint1;
        this.cameraControlPoint2 = cameraControlPoint2;
        this.cameraEndPoint = cameraEndPoint;
    }

    @Override
    public void tick(double progress, Cutscene cutscene) {
        double t = Math.max(0, Math.min(1, progress));

        // Bezier for position (player path)
        double px = Math.pow(1 - t, 3) * startPoint.getX()
                + 3 * Math.pow(1 - t, 2) * t * controlPoint1.getX()
                + 3 * (1 - t) * Math.pow(t, 2) * controlPoint2.getX()
                + Math.pow(t, 3) * endPoint.getX();
        double py = Math.pow(1 - t, 3) * startPoint.getY()
                + 3 * Math.pow(1 - t, 2) * t * controlPoint1.getY()
                + 3 * (1 - t) * Math.pow(t, 2) * controlPoint2.getY()
                + Math.pow(t, 3) * endPoint.getY();
        double pz = Math.pow(1 - t, 3) * startPoint.getZ()
                + 3 * Math.pow(1 - t, 2) * t * controlPoint1.getZ()
                + 3 * (1 - t) * Math.pow(t, 2) * controlPoint2.getZ()
                + Math.pow(t, 3) * endPoint.getZ();

        // Bezier for camera yaw/pitch
        Location camStart = cameraStartPoint != null ? cameraStartPoint : startPoint;
        Location camCtrl1 = cameraControlPoint1 != null ? cameraControlPoint1 : controlPoint1;
        Location camCtrl2 = cameraControlPoint2 != null ? cameraControlPoint2 : controlPoint2;
        Location camEnd = cameraEndPoint != null ? cameraEndPoint : endPoint;

        float cyaw = (float) (
                Math.pow(1 - t, 3) * camStart.getYaw()
                        + 3 * Math.pow(1 - t, 2) * t * camCtrl1.getYaw()
                        + 3 * (1 - t) * Math.pow(t, 2) * camCtrl2.getYaw()
                        + Math.pow(t, 3) * camEnd.getYaw()
        );
        float cpitch = (float) (
                Math.pow(1 - t, 3) * camStart.getPitch()
                        + 3 * Math.pow(1 - t, 2) * t * camCtrl1.getPitch()
                        + 3 * (1 - t) * Math.pow(t, 2) * camCtrl2.getPitch()
                        + Math.pow(t, 3) * camEnd.getPitch()
        );

        Location combinedLoc = new Location(startPoint.getWorld(), px, py, pz, cyaw, cpitch);

        if (cutscene.getCamera() != null) {
            cutscene.getCamera().setLocation(combinedLoc);
        }
    }

    @Override
    public void cleanup() {

    }


}
