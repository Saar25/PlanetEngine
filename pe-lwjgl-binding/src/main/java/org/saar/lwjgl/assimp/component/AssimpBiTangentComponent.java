package org.saar.lwjgl.assimp.component;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;

public class AssimpBiTangentComponent extends AssimpComponent3D {

    @Override
    public AIVector3D.Buffer getBuffer(AIMesh aiMesh) {
        return aiMesh.mBitangents();
    }

    @Override
    public String exceptionMessage() {
        return "BiTangent data not found";
    }
}
