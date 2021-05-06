package org.saar.lwjgl.assimp.component;

import org.joml.Vector3fc;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;
import org.saar.lwjgl.assimp.AssimpComponent;
import org.saar.lwjgl.assimp.AssimpUtil;
import org.saar.maths.utils.Vector3;

public class AssimpPositionComponent implements AssimpComponent {

    private final AIVector3D.Buffer buffer;

    public AssimpPositionComponent(AIVector3D.Buffer buffer) {
        this.buffer = buffer;
    }

    public static AssimpPositionComponent of(AIMesh aiMesh) {
        final AIVector3D.Buffer buffer = aiMesh.mVertices();
        AssimpUtil.requiredNotNull(buffer, "Position data not found");

        return new AssimpPositionComponent(buffer);
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
