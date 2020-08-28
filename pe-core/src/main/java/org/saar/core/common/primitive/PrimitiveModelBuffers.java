package org.saar.core.common.primitive;

import org.saar.core.model.*;
import org.saar.core.model.loader.AbstractModelBuffers;
import org.saar.core.model.loader.ModelBuffer;
import org.saar.core.model.loader.ModelBuffers;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.IndexBuffer;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;

public class PrimitiveModelBuffers extends AbstractModelBuffers<PrimitiveNode, PrimitiveVertex> implements ModelBuffers {

    private final ModelBuffer instanceBuffer;
    private final ModelBuffer vertexBuffer;
    private final ModelBuffer indexBuffer;

    private final Model model;

    public PrimitiveModelBuffers(int vertices, int indices, int instances, Attribute[] vertexAttributes, Attribute[] instanceAttributes) {
        this.instanceBuffer = loadDataBuffer(new DataBuffer(VboUsage.STATIC_DRAW), instances, instanceAttributes);
        this.vertexBuffer = loadDataBuffer(new DataBuffer(VboUsage.STATIC_DRAW), vertices, vertexAttributes);
        this.indexBuffer = loadIndexBuffer(new IndexBuffer(VboUsage.STATIC_DRAW), indices);

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

    @Override
    public void writeInstance(PrimitiveNode instance) {
        for (GlPrimitive value : instance.getValues()) {
            value.write(this.instanceBuffer.getWriter());
        }
    }

    @Override
    public void writeVertex(PrimitiveVertex vertex) {
        for (GlPrimitive value : vertex.getValues()) {
            value.write(this.vertexBuffer.getWriter());
        }
    }

    @Override
    public void writeIndex(int index) {
        this.indexBuffer.getWriter().write(index);
    }

    public void load(PrimitiveVertex[] vertices, int[] indices, PrimitiveNode[] instances) {
        if (instances.length > 0) {
            this.writeInstances(instances);
        }
        if (vertices.length > 0) {
            this.writeVertices(vertices);
        }
        if (indices.length > 0) {
            this.writeIndices(indices);
        }
        this.updateBuffers();
    }

    @Override
    public Model getModel() {
        return this.model;
    }
}
