package org.saar.core.common.r2d;

import org.saar.core.model.ElementsMesh;
import org.saar.core.model.Mesh;
import org.saar.core.model.loader.AbstractModelBuffers;
import org.saar.core.model.loader.ModelBuffer;
import org.saar.core.model.loader.ModelWriters;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.objects.vbos.IndexBuffer;

public class ModelBuffers2DOneVbo extends AbstractModelBuffers implements ModelBuffers2D {

    private final Mesh mesh;

    private final ModelWriter2D writer;

    public ModelBuffers2DOneVbo(int vertices, int indices) {
        final ModelBuffer vertexBuffer = loadDataBuffer(new DataBuffer(VboUsage.STATIC_DRAW), vertices, attributes);
        final ModelBuffer indexBuffer = loadIndexBuffer(new IndexBuffer(VboUsage.STATIC_DRAW), indices);

        this.mesh = new ElementsMesh(this.vao, RenderMode.TRIANGLES, indices, DataType.U_INT);

        this.writer = new ModelWriter2D(vertexBuffer.getWriter(), vertexBuffer.getWriter(), indexBuffer.getWriter());
    }

    @Override
    public void load(Vertex2D[] vertices, int[] indices) {
        ModelWriters.writeVertices(this.writer, vertices);
        ModelWriters.writeIndices(this.writer, indices);
        updateBuffers();
        deleteBuffers();
    }

    @Override
    public Mesh getMesh() {
        return this.mesh;
    }
}
