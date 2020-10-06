package org.saar.core.camera.projection;

import org.joml.Matrix4fc;
import org.saar.core.camera.Projection;
import org.saar.maths.wrapper.Matrix4fWrapper;

public class NullProjection implements Projection {

    private static final NullProjection instance = new NullProjection();

    private final Matrix4fWrapper matrix = new Matrix4fWrapper();

    private NullProjection() {
    }

    public static NullProjection getInstance() {
        return NullProjection.instance;
    }

    @Override
    public Matrix4fc getMatrix() {
        return this.matrix.getReadonly();
    }
}
