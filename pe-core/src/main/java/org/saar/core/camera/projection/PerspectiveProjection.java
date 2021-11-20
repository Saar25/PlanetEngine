package org.saar.core.camera.projection;

import org.joml.Matrix4f;
import org.joml.Matrix4fc;
import org.saar.core.camera.Projection;
import org.saar.maths.utils.Matrix4;

public abstract class PerspectiveProjection implements Projection {

    private final Matrix4f matrix = Matrix4.create();

    public abstract float getFov();

    public abstract float getWidth();

    public abstract float getHeight();

    public abstract float getNear();

    public abstract float getFar();

    @Override
    public final Matrix4fc getMatrix() {
        return Matrix4.ofProjection((float) Math.toRadians(getFov()),
                getWidth(), getHeight(), getNear(), getFar(), this.matrix);
    }
}
