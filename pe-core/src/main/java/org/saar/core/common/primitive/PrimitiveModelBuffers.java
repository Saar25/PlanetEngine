package org.saar.core.common.primitive;

import org.saar.core.model.loader.ModelBuffersSingleVbo;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;

public class PrimitiveModelBuffers extends ModelBuffersSingleVbo<PrimitiveNode, PrimitiveVertex> {

    public PrimitiveModelBuffers(int vertices, int indices, int instances, Attribute[] vertexAttributes, Attribute[] instanceAttributes) {
        super(vertices, indices, instances, vertexAttributes, instanceAttributes);
    }

    @Override
    public void writeInstance(PrimitiveNode instance) {
        for (GlPrimitive value : instance.getValues()) {
            value.write(instanceWriter());
        }
    }

    @Override
    public void writeVertex(PrimitiveVertex vertex) {
        for (GlPrimitive value : vertex.getValues()) {
            value.write(vertexWriter());
        }
    }

    @Override
    public void writeIndex(int index) {
        indexWriter().write(index);
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
}
