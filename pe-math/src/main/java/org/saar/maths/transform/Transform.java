package org.saar.maths.transform;

import org.joml.Matrix4f;

public interface Transform {

    Matrix4f getTransformationMatrix();

    Position getPosition();

    Rotation getRotation();

    Scale getScale();

}
