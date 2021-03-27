package org.saar.lwjgl.assimp.component;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;
import org.saar.lwjgl.opengl.objects.buffers.BufferObjectWrapper;

public class AssimpNormalComponent extends AssimpComponent3D {

    public AssimpNormalComponent(BufferObjectWrapper vbo) {
        super(vbo);
    }

    @Override
    public AIVector3D.Buffer getBuffer(AIMesh aiMesh) {
        return aiMesh.mNormals();
    }

    @Override
    public String exceptionMessage() {
        return "Normals data not found";
    }
}
