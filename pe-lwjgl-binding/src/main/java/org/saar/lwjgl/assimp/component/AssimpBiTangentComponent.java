package org.saar.lwjgl.assimp.component;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;
import org.saar.lwjgl.opengl.objects.buffers.BufferObjectWrapper;

public class AssimpBiTangentComponent extends AssimpComponent3D {

    public AssimpBiTangentComponent(BufferObjectWrapper vbo) {
        super(vbo);
    }

    @Override
    public AIVector3D.Buffer getBuffer(AIMesh aiMesh) {
        return aiMesh.mBitangents();
    }

    @Override
    public String exceptionMessage() {
        return "BiTangent data not found";
    }
}
