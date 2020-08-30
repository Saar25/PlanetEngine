package org.saar.core.camera;

import org.joml.Matrix4fc;
import org.saar.maths.objects.Transform;

public interface ICamera {

    Matrix4fc getViewMatrix();

    Transform getTransform();

    Projection getProjection();

}
