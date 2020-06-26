package org.saar.core.model.data;

import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.IVbo;
import org.saar.lwjgl.opengl.objects.IndexBuffer;

public class IndexModelData {

    private final int[] indices;

    public IndexModelData(int... indices) {
        this.indices = indices;
    }

    public int indices() {
        return this.indices.length;
    }

    public IVbo vbo() {
        final IndexBuffer indexBuffer = new IndexBuffer(VboUsage.STATIC_DRAW);
        indexBuffer.allocateInt(this.indices.length);
        indexBuffer.storeData(0, this.indices);
        return indexBuffer;
    }
}
