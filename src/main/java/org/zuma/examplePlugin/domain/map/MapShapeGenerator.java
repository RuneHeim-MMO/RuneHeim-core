package org.zuma.examplePlugin.domain.map;

import com.flowpowered.math.vector.Vector2d;
import de.bluecolored.bluemap.api.math.Shape;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapShapeGenerator {

    /**
     * Generates points on a circle polygon around a center point.
     *
     * @param centerX Center X coordinate
     * @param centerZ Center Z coordinate
     * @param radius Radius of the circle
     * @param pointsCount Number of points to generate around the circle
     * @return List of JSONObjects with x,z coordinates suitable for BlueMap shape
     */
    public static List<JSONObject> generateCircleShape(int centerX, int centerZ, double radius, int pointsCount) {
        List<JSONObject> shapePoints = new ArrayList<>();

        for (int i = 0; i < pointsCount; i++) {
            double angle = 2 * Math.PI * i / pointsCount;
            int x = (int) Math.round(centerX + radius * Math.cos(angle));
            int z = (int) Math.round(centerZ + radius * Math.sin(angle));

            JSONObject point = new JSONObject();
            point.put("x", x);
            point.put("z", z);
            shapePoints.add(point);
        }

        return shapePoints;
    }

    public static Shape generateCircleShapeAsShape(int centerX, int centerZ, double radius, int pointsCount) {
        List<Vector2d> points = new ArrayList<>();

        for (int i = 0; i < pointsCount; i++) {
            double angle = 2 * Math.PI * i / pointsCount;
            double x = centerX + radius * Math.cos(angle);
            double z = centerZ + radius * Math.sin(angle);
            points.add(new Vector2d(x, z));
        }

        return new Shape(points);
    }

}
