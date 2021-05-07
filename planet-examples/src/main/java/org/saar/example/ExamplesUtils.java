package org.saar.example;

import org.saar.core.common.r3d.R3D;
import org.saar.core.common.r3d.Vertex3D;
import org.saar.maths.utils.Vector3;

public final class ExamplesUtils {

    public static final Vertex3D[] cubeVertices = new Vertex3D[]{ // xyz position, xyz normal,
            R3D.vertex(Vector3.of(-0.5f, -0.5f, -0.5f), Vector3.of(+0, +0, -1), Vector3.of(0.5f, 0.5f, 0.0f)), // 0
            R3D.vertex(Vector3.of(-0.5f, +0.5f, -0.5f), Vector3.of(+0, +1, +0), Vector3.of(0.5f, 1.0f, 0.5f)), // 1
            R3D.vertex(Vector3.of(+0.5f, +0.5f, -0.5f), Vector3.of(+1, +0, +0), Vector3.of(1.0f, 0.5f, 0.5f)), // 2
            R3D.vertex(Vector3.of(+0.5f, -0.5f, -0.5f), Vector3.of(+0, -1, +0), Vector3.of(0.5f, 0.0f, 0.5f)), // 3
            R3D.vertex(Vector3.of(-0.5f, -0.5f, +0.5f), Vector3.of(-1, +0, +0), Vector3.of(0.0f, 0.5f, 0.5f)), // 4
            R3D.vertex(Vector3.of(-0.5f, +0.5f, +0.5f), Vector3.of(+0, +0, +0), Vector3.of(0.5f, 0.5f, 0.5f)), // 5
            R3D.vertex(Vector3.of(+0.5f, +0.5f, +0.5f), Vector3.of(+0, +0, +0), Vector3.of(0.5f, 0.5f, 0.5f)), // 6
            R3D.vertex(Vector3.of(+0.5f, -0.5f, +0.5f), Vector3.of(+0, +0, +1), Vector3.of(0.5f, 0.5f, 1.0f)), // 7
    };

    public static final int[] cubeIndices = {
            0, 1, 2, 0, 2, 3, // back   , PV: 0
            4, 5, 1, 4, 1, 0, // left   , PV: 4
            7, 6, 5, 7, 5, 4, // front  , PV: 7
            2, 6, 7, 2, 7, 3, // right  , PV: 2
            1, 5, 6, 1, 6, 2, // top    , PV: 1
            3, 7, 4, 3, 4, 0, // bottom , PV: 3
    };

    private ExamplesUtils() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }
}
