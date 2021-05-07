package org.saar.lwjgl.assimp.component;

import org.joml.Vector2fc;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;
import org.saar.lwjgl.assimp.AssimpComponent;
import org.saar.lwjgl.assimp.AssimpUtil;
import org.saar.maths.utils.Vector2;

public class AssimpTexCoordComponent implements AssimpComponent {

    private final AIVector3D.Buffer buffer;

    public AssimpTexCoordComponent(AIVector3D.Buffer buffer) {
        this.buffer = buffer;
    }

    public static AssimpTexCoordComponent of(AIMesh aiMesh) {
        final AIVector3D.Buffer buffer = aiMesh.mTextureCoords(0);
        AssimpUtil.requiredNotNull(buffer, "Texture coords data not found");

        return new AssimpTexCoordComponent(buffer);
    }

    public Vector2fc next() {
        final AIVector3D current = this.buffer.get();
        return Vector2.of(current.x(), 1 - current.y());
    }

    @Override
    public int count() {
        return this.buffer.limit();
    }
}
