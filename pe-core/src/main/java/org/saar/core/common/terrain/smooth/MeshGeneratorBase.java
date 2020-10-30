package org.saar.core.common.terrain.smooth;

import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.saar.core.common.terrain.colour.ColourGenerator;
import org.saar.core.common.terrain.height.HeightGenerator;
import org.saar.maths.utils.Maths;
import org.saar.maths.utils.Vector3;

public abstract class MeshGeneratorBase extends MeshGenerator {

    private static final Vector3f v1 = Vector3.create();
    private static final Vector3f v2 = Vector3.create();
    private static final Vector3f v3 = Vector3.create();

    protected abstract HeightGenerator getHeightGenerator();

    protected abstract ColourGenerator getColourGenerator();

    protected abstract float xPosition();

    protected abstract float zPosition();

    private Vector3fc vertexPosition(float x, float z, Vector3f dest) {
        final float y = getHeightGenerator().generateHeight(
                this.xPosition() + x, this.zPosition() + z);
        return dest.set(x, y, z);
    }

    protected final Vector3fc vertexPosition(float x, float z) {
        return vertexPosition(x, z, v1);
    }

    protected final Vector3fc vertexNormal(Vector3fc position, float xOffset, float zOffset) {
        final Vector3fc p2 = vertexPosition(position.x(), position.z() + xOffset, v2);
        final Vector3fc p3 = vertexPosition(position.x() + zOffset, position.z(), v3);
        return Maths.calculateNormal(position, p2, p3);
    }

    protected final Vector3fc vertexColour(Vector3fc v) {
        final float tx = xPosition() + v.x();
        final float tz = zPosition() + v.z();
        return getColourGenerator().generateColour(tx, v.y(), tz);
    }

}
