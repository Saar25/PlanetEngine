package org.saar.core.common.primitive;

import org.saar.core.model.Model;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrimitiveModelLoader {

    private final PrimitiveVertex[] vertices;
    private final int[] indices;
    private final PrimitiveNode[] instances;

    public PrimitiveModelLoader(PrimitiveVertex[] vertices, int[] indices, PrimitiveNode[] instances) {
        this.vertices = vertices;
        this.indices = indices;
        this.instances = instances;
    }

    public PrimitiveModelLoader(PrimitiveVertex[] vertices, PrimitiveNode[] instances) {
        this.vertices = vertices;
        this.indices = null;
        this.instances = instances;
    }

    public PrimitiveModelLoader(PrimitiveVertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
        this.instances = null;
    }

    public PrimitiveModelLoader(PrimitiveVertex[] vertices) {
        this.vertices = vertices;
        this.indices = null;
        this.instances = null;
    }

    private Attribute[] vertexAttributes() {
        if (this.vertices.length > 0) {
            final PrimitiveVertex vertex = this.vertices[0];
            final List<Attribute> attributes = new ArrayList<>();
            for (GlPrimitive value : vertex.getValues()) {
                Collections.addAll(attributes, value.attribute(attributes.size(), false, 0));
            }
            return attributes.toArray(new Attribute[0]);
        }
        return new Attribute[0];
    }

    private Attribute[] nodeAttributes(int attributesIndex) {
        if (this.instances != null && this.instances.length > 0) {
            final PrimitiveNode node = this.instances[0];
            final List<Attribute> attributes = new ArrayList<>();
            for (GlPrimitive value : node.getValues()) {
                Collections.addAll(attributes, value.attribute(attributesIndex + attributes.size(), false, 1));
            }
            return attributes.toArray(new Attribute[0]);
        }
        return new Attribute[0];
    }

    public Model createModel() {
        final Attribute[] vertexAttributes = vertexAttributes();
        final Attribute[] nodeAttributes = nodeAttributes(vertexAttributes.length);

        final int indicesLength = indices == null ? 0 : indices.length;
        final int instancesLength = instances == null ? 0 : instances.length;

        final PrimitiveModelBuffers buffers = new PrimitiveModelBuffers(vertices.length,
                indicesLength, instancesLength, vertexAttributes, nodeAttributes);

        buffers.load(vertices, indices, instances);

        return buffers.getModel();

    }
}
