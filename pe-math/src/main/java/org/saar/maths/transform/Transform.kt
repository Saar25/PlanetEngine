package org.saar.maths.transform;

import org.joml.Matrix4fc;

public interface Transform {

    Matrix4fc getTransformationMatrix();

    Position getPosition();

    Rotation getRotation();

    Scale getScale();

}
