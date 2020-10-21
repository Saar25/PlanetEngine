package org.saar.lwjgl.assimp.component;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;
import org.saar.lwjgl.assimp.AssimpComponent;
import org.saar.lwjgl.assimp.AssimpUtil;
import org.saar.lwjgl.opengl.objects.buffers.BufferObjectWrapper;

public class AssimpTexCoordComponent extends AssimpComponent {

    private final int index;
    private final BufferObjectWrapper vbo;

    private AIVector3D.Buffer buffer;

    public AssimpTexCoordComponent(int index, BufferObjectWrapper vbo) {
        this.index = index;
        this.vbo = vbo;
    }

    @Override
    public void init(AIMesh aiMesh) {
        this.buffer = aiMesh.mTextureCoords(this.index);
        AssimpUtil.requiredNotNull(this.buffer, "Tex coords data not found");

        final int bytes = 2 * Float.BYTES;
        this.vbo.allocateMore(bytes * aiMesh.mVertices().limit());
    }

    @Override
    public void next() {
        final AIVector3D value = this.buffer.get();
        this.vbo.getWriter().write(value.x());
        this.vbo.getWriter().write(1 - value.y());
    }
}
