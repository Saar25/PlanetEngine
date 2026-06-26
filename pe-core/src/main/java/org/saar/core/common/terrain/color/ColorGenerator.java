package org.saar.core.common.terrain.color;

import org.joml.Vector3fc;

public interface ColorGenerator {

    Vector3fc generateColor(Vector3fc position, Vector3fc normal);

}
