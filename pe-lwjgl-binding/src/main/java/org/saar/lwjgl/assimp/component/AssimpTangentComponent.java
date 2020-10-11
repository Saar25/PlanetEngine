package org.saar.lwjgl.assimp.component;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;

public class AssimpTangentComponent extends AssimpComponent3D {

    @Override
    public AIVector3D.Buffer getBuffer(AIMesh aiMesh) {
        return aiMesh.mTangents();
    }

    @Override
    public String exceptionMessage() {
        return "Tangent data not found";
    }
}
