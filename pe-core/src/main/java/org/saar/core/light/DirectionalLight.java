package org.saar.core.light;

import org.joml.Vector3f;
import org.saar.maths.utils.Vector3;

public class DirectionalLight extends LightNodeBase implements IDirectionalLight {

    private final Vector3f direction = Vector3.create();
    private final Vector3f colour = Vector3.create();

    @Override
    public Vector3f getDirection() {
        return this.direction;
    }

    @Override
    public Vector3f getColour() {
        return this.colour;
    }

}
