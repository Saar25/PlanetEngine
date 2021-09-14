package org.saar.core.light;

import org.joml.Vector3f;
import org.saar.maths.utils.Vector3;

public class PointLight implements IPointLight {

    private final Vector3f position = Vector3.create();
    private final Vector3f radiuses = Vector3.create();
    private final Vector3f colour = Vector3.create();

    @Override
    public Vector3f getPosition() {
        return this.position;
    }

    @Override
    public Vector3f getRadiuses() {
        return this.radiuses;
    }

    @Override
    public Vector3f getColour() {
        return this.colour;
    }
}
