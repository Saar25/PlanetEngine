package org.saar.core.model.loader;

import org.saar.core.model.InstancedElementsModel;
import org.saar.core.model.Model;
import org.saar.core.model.Vertex;
import org.saar.core.node.Node;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vbos.DataBuffer;
import org.saar.lwjgl.opengl.objects.vbos.IndexBuffer;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public abstract class ModelBuffersSingleVbo<N extends Node, V extends Vertex> extends AbstractModelBuffers<N, V> {

    private final ModelBuffer instanceBuffer;
    private final ModelBuffer vertexBuffer;
    private final ModelBuffer indexBuffer;

    private final Model model;

    public ModelBuffersSingleVbo(int vertices, int indices, int instances, Attribute[] vertexAttributes, Attribute[] instanceAttributes) {
        this.instanceBuffer = loadDataBuffer(new DataBuffer(VboUsage.STATIC_DRAW),
                instances, instanceAttributes);
        this.vertexBuffer = loadDataBuffer(new DataBuffer(VboUsage.STATIC_DRAW),
                vertices, vertexAttributes);
        this.indexBuffer = loadIndexBuffer(new IndexBuffer(VboUsage.STATIC_DRAW), indices);

        this.model = new InstancedElementsModel(this.vao, RenderMode.TRIANGLES, indices, DataType.U_INT, instances);
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
        if (instances != null && instances.length != 0) {
            this.writeInstances(instances);
        }
        if (vertices != null && vertices.length != 0) {
            this.writeVertices(vertices);
        }
        if (indices != null && indices.length != 0) {
            this.writeIndices(indices);
        }
        this.updateBuffers();
        this.deleteBuffers();
    }

    @Override
    public Model getModel() {
        return this.model;
    }
}
