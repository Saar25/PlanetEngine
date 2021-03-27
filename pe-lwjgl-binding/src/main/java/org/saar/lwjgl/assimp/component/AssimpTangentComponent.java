package org.saar.lwjgl.assimp.component;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;
import org.saar.lwjgl.opengl.objects.buffers.BufferObjectWrapper;

public class AssimpTangentComponent extends AssimpComponent3D {

    public AssimpTangentComponent(BufferObjectWrapper vbo) {
        super(vbo);
    }

    @Override
    public AIVector3D.Buffer getBuffer(AIMesh aiMesh) {
        return aiMesh.mTangents();
    }

    @Override
    public String exceptionMessage() {
        return "Tangent data not found";
    }
}
