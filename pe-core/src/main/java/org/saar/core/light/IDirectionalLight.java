package org.saar.core.light;

import org.joml.Vector3fc;

public interface IDirectionalLight extends LightNode {

    Vector3fc getDirection();

    Vector3fc getColour();

}
