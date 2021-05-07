package org.saar.lwjgl.assimp.component;

import org.joml.Vector3fc;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;
import org.saar.lwjgl.assimp.AssimpComponent;
import org.saar.lwjgl.assimp.AssimpUtil;
import org.saar.maths.utils.Vector3;

public class AssimpNormalComponent implements AssimpComponent {

    private final AIVector3D.Buffer buffer;

    public AssimpNormalComponent(AIVector3D.Buffer buffer) {
        this.buffer = buffer;
    }

    public static AssimpNormalComponent of(AIMesh aiMesh) {
        final AIVector3D.Buffer buffer = aiMesh.mNormals();
        AssimpUtil.requiredNotNull(buffer, "Normal data not found");

        return new AssimpNormalComponent(buffer);
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
