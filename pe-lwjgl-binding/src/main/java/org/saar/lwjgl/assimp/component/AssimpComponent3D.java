package org.saar.lwjgl.assimp.component;

import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIVector3D;
import org.saar.lwjgl.assimp.AssimpComponent;
import org.saar.lwjgl.assimp.AssimpUtil;
import org.saar.lwjgl.opengl.objects.vbos.VboWrapper;

public abstract class AssimpComponent3D extends AssimpComponent {

    private final VboWrapper vbo;
    private AIVector3D.Buffer buffer;

    public AssimpComponent3D(VboWrapper vbo) {
        this.vbo = vbo;
    }

    public abstract AIVector3D.Buffer getBuffer(AIMesh aiMesh);

    public abstract String exceptionMessage();

    @Override
    public void init(AIMesh aiMesh) {
        this.buffer = getBuffer(aiMesh);
        AssimpUtil.requiredNotNull(this.buffer, exceptionMessage());

        final int bytes = 3 * Float.BYTES;
        this.vbo.allocateMore(bytes * aiMesh.mVertices().limit());
    }

    @Override
    public void next() {
        final AIVector3D value = this.buffer.get();
        this.vbo.getWriter().write(value.x());
        this.vbo.getWriter().write(value.y());
        this.vbo.getWriter().write(value.z());
    }
}