package org.saar.core.common.terrain.colour;

import org.joml.Vector3fc;

public interface ColourGenerator {

    Vector3fc generateColour(float x, float y, float z);

}
