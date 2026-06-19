package org.saar.core.camera.projection;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.saar.core.camera.Projection;
import org.saar.maths.utils.Matrix4;

public class NullProjection implements Projection {

    private static final NullProjection instance = new NullProjection();

    private final Matrix4f matrix = Matrix4.create();

    private NullProjection() {
    }

    public static NullProjection getInstance() {
        return NullProjection.instance;
    }

    @Override
    public Matrix4fc getMatrix() {
        return this.matrix;
    }
}
