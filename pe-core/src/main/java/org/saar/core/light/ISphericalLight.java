package org.saar.core.light;

import org.joml.Vector3fc;

public interface ISphericalLight extends LightNode {

    Vector3fc getPosition();

    Vector3fc getRadiuses();

    Vector3fc getColour();

}
