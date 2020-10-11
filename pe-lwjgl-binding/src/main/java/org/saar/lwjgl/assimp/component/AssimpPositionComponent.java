package org.saar.lwjgl.assimp.component;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;

public class AssimpPositionComponent extends AssimpComponent3D {

    @Override
    public AIVector3D.Buffer getBuffer(AIMesh aiMesh) {
        return aiMesh.mVertices();
    }

    @Override
    public String exceptionMessage() {
        return "Positions data not found";
    }
}
