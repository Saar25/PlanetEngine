package org.saar.lwjgl.assimp.component;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;
import org.saar.lwjgl.opengl.objects.vbos.VboWrapper;

public class AssimpPositionComponent extends AssimpComponent3D {

    public AssimpPositionComponent(VboWrapper writer) {
        super(writer);
    }

    @Override
    public AIVector3D.Buffer getBuffer(AIMesh aiMesh) {
        return aiMesh.mVertices();
    }

    @Override
    public String exceptionMessage() {
        return "Positions data not found";
    }
}
