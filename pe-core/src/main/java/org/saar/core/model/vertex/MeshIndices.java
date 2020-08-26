package org.saar.core.model.vertex;

import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.IVbo;
import org.saar.lwjgl.opengl.objects.IndexBuffer;

public class MeshIndices {

    private final int[] indices;

    public MeshIndices(int... indices) {
        this.indices = indices;
    }

    public IVbo vbo() {
        final IndexBuffer vbo = new IndexBuffer(VboUsage.STATIC_DRAW);
        vbo.allocateInt(this.indices.length);
        vbo.storeData(0, this.indices);
        return vbo;
    }

}
