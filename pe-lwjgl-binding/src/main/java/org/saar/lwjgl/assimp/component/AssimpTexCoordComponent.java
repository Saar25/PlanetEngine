package org.saar.lwjgl.assimp.component;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;
import org.saar.lwjgl.assimp.AssimpComponent;
import org.saar.lwjgl.assimp.AssimpUtil;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public class AssimpTexCoordComponent extends AssimpComponent {

    private final int index;

    private AIVector3D.Buffer buffer;

    public AssimpTexCoordComponent(int index) {
        this.index = index;
    }

    @Override
    public int bytes() {
        return 2 * Float.BYTES;
    }

    @Override
    public void init(AIMesh aiMesh) {
        this.buffer = aiMesh.mTextureCoords(this.index);
        AssimpUtil.requiredNotNull(this.buffer, "Tex coords data not found");
    }

    @Override
    public void next(BufferWriter writer) {
        final AIVector3D value = this.buffer.get();
        writer.write(value.x());
        writer.write(1 - value.y());
    }
}
