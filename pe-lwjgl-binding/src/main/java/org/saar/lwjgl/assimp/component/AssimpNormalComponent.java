package org.saar.lwjgl.assimp.component;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;

public class AssimpNormalComponent extends AssimpComponent3D {

    @Override
    public AIVector3D.Buffer getBuffer(AIMesh aiMesh) {
        return aiMesh.mNormals();
    }

    @Override
    public String exceptionMessage() {
        return "Normals data not found";
    }
}
