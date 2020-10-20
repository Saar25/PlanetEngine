package org.saar.lwjgl.assimp.component;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;
import org.saar.lwjgl.opengl.objects.vbos.VboWrapper;

public class AssimpTangentComponent extends AssimpComponent3D {

    public AssimpTangentComponent(VboWrapper vbo) {
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
