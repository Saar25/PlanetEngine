package org.saar.core.renderer.r2d;

import org.saar.core.model.ElementsModel;
import org.saar.core.model.Model;
import org.saar.core.model.loader.AbstractModelBuffers;
import org.saar.core.model.loader.ModelBuffer;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.objects.vbos.IndexBuffer;

public class ModelBuffers2DOneVbo extends AbstractModelBuffers<Node2D, Vertex2D> implements ModelBuffers2D {

    private final ModelBuffer vertexBuffer;
    private final ModelBuffer indexBuffer;

    private final Model model;

    public ModelBuffers2DOneVbo(int vertices, int indices) {
        this.vertexBuffer = loadDataBuffer(new DataBuffer(VboUsage.STATIC_DRAW), vertices, attributes);
        this.indexBuffer = loadIndexBuffer(new IndexBuffer(VboUsage.STATIC_DRAW), indices);

        this.model = new ElementsModel(this.vao, RenderMode.TRIANGLES, indices, DataType.U_INT);
    }

    @Override
    public void writeInstance(Node2D instance) {
    }

    @Override
    public void writeVertex(Vertex2D vertex) {
        this.vertexBuffer.getWriter().write(vertex.getPosition2f());
        this.vertexBuffer.getWriter().write(vertex.getColour3f());
    }

    @Override
    public void writeIndex(int index) {
        this.indexBuffer.getWriter().write(index);
    }

    @Override
    public void load(Vertex2D[] vertices, int[] indices) {
        writeVertices(vertices);
        writeIndices(indices);
        updateBuffers();
    }

    @Override
    public Model getModel() {
        return this.model;
    }
}
