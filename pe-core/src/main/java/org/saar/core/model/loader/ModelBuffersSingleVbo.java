package org.saar.core.model.loader;

import org.saar.core.model.*;
import org.saar.core.node.Node;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.IndexBuffer;
import org.saar.lwjgl.opengl.objects.Vao;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public abstract class ModelBuffersSingleVbo<N extends Node, V extends Vertex> extends AbstractModelBuffers<N, V> {

    protected final Vao vao = Vao.create();

    private final ModelBuffer instanceBuffer;
    private final ModelBuffer vertexBuffer;
    private final ModelBuffer indexBuffer;

    private final Model model;

    public ModelBuffersSingleVbo(int vertices, int indices, int instances, Attribute[] vertexAttributes, Attribute[] instanceAttributes) {
        this.instanceBuffer = instances > 0 ? loadDataBuffer(
                new DataBuffer(VboUsage.STATIC_DRAW), instances, instanceAttributes) : null;
        this.vertexBuffer = vertices > 0 ? loadDataBuffer(
                new DataBuffer(VboUsage.STATIC_DRAW), vertices, vertexAttributes) : null;
        this.indexBuffer = indices > 0 ? loadIndexBuffer(
                new IndexBuffer(VboUsage.STATIC_DRAW), indices) : null;

        this.model = createModel(vertices, indices, instances);
    }

    private Model createModel(int vertices, int indices, int instances) {
        if (instances > 0) return indices > 0
                ? new InstancedElementsModel(this.vao, RenderMode.TRIANGLES, indices, DataType.U_INT, instances)
                : new InstancedArraysModel(this.vao, RenderMode.TRIANGLES, vertices, instances);
        else return indices > 0
                ? new ElementsModel(this.vao, RenderMode.TRIANGLES, indices, DataType.U_INT)
                : new ArraysModel(this.vao, RenderMode.TRIANGLES, vertices);
    }

    protected final BufferWriter instanceWriter() {
        return this.instanceBuffer.getWriter();
    }

    protected final BufferWriter vertexWriter() {
        return this.vertexBuffer.getWriter();
    }

    protected final BufferWriter indexWriter() {
        return this.indexBuffer.getWriter();
    }

    public void load(V[] vertices, int[] indices, N[] instances) {
        if (instances != null && instances.length > 0) {
            this.writeInstances(instances);
        }
        if (vertices != null && vertices.length > 0) {
            this.writeVertices(vertices);
        }
        if (indices != null && indices.length > 0) {
            this.writeIndices(indices);
        }
        this.updateBuffers();
    }

    @Override
    public Model getModel() {
        return this.model;
    }
}
