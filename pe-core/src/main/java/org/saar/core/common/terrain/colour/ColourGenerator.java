package org.saar.core.common.terrain.colour;

import org.joml.Vector3fc;

public interface ColourGenerator {

    Vector3fc generateColour(Vector3fc position, Vector3fc normal);

}
