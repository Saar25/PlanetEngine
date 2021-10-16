package org.saar.lwjgl.assimp.component;

import org.joml.Vector3fc;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;
import org.saar.lwjgl.assimp.AssimpComponent;
import org.saar.lwjgl.assimp.AssimpUtil;
import org.saar.maths.utils.Vector3;

public class AssimpTangentComponent implements AssimpComponent {

    private final AIVector3D.Buffer buffer;

    public AssimpTangentComponent(AIVector3D.Buffer buffer) {
        this.buffer = buffer;
    }

    public static AssimpTangentComponent of(AIMesh aiMesh) {
        final AIVector3D.Buffer buffer = aiMesh.mTangents();
        AssimpUtil.requiredNotNull(buffer, "Tangent data not found");

        return new AssimpTangentComponent(buffer);
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
