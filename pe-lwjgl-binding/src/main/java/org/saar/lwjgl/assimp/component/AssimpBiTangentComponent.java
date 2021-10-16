package org.saar.lwjgl.assimp.component;

import org.joml.Vector3fc;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;
import org.saar.lwjgl.assimp.AssimpComponent;
import org.saar.lwjgl.assimp.AssimpUtil;
import org.saar.maths.utils.Vector3;

public class AssimpBiTangentComponent implements AssimpComponent {

    private final AIVector3D.Buffer buffer;

    public AssimpBiTangentComponent(AIVector3D.Buffer buffer) {
        this.buffer = buffer;
    }

    public static AssimpBiTangentComponent of(AIMesh aiMesh) {
        final AIVector3D.Buffer buffer = aiMesh.mBitangents();
        AssimpUtil.requiredNotNull(buffer, "Bi tangent data not found");

        return new AssimpBiTangentComponent(buffer);
    }

    public Vector3fc next() {
        final AIVector3D current = this.buffer.get();
        return Vector3.of(current.x(), current.y(), current.z());
    }

    @Override
    public int count() {
        return this.buffer.limit();
    }
}
