package org.saar.lwjgl.assimp.component;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;
import org.saar.lwjgl.assimp.AssimpComponent;
import org.saar.lwjgl.assimp.AssimpUtil;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public abstract class AssimpComponent3D extends AssimpComponent {

    private AIVector3D.Buffer buffer;

    public abstract AIVector3D.Buffer getBuffer(AIMesh aiMesh);

    public abstract String exceptionMessage();

    @Override
    public int bytes() {
        return 3 * Float.BYTES;
    }

    @Override
    public void init(AIMesh aiMesh) {
        this.buffer = getBuffer(aiMesh);

        AssimpUtil.requiredNotNull(this.buffer, exceptionMessage());
    }

    @Override
    public void next(BufferWriter writer) {
        final AIVector3D value = this.buffer.get();
        writer.write(value.x());
        writer.write(value.y());
        writer.write(value.z());
    }
}