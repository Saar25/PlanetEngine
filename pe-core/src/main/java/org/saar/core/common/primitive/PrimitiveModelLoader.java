package org.saar.core.common.primitive;

import org.saar.core.model.Model;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PrimitiveModelLoader {

    private final PrimitiveMesh mesh;
    private final List<PrimitiveNode> nodes;

    public PrimitiveModelLoader(PrimitiveMesh mesh, PrimitiveNode... nodes) {
        this.mesh = mesh;
        this.nodes = Arrays.asList(nodes);
    }

    private Attribute[] vertexAttributes(int attributesIndex) {
        if (this.mesh.getVertices().size() > 0) {
            final PrimitiveVertex vertex = this.mesh.getVertices().get(0);
            final List<Attribute> attributes = new ArrayList<>();
            for (GlPrimitive value : vertex.getValues()) {
                Collections.addAll(attributes, value.attribute(attributesIndex + attributes.size(), false));
            }
            return attributes.toArray(new Attribute[0]);
        }
        return new Attribute[0];
    }

    private Attribute[] nodeAttributes(int attributesIndex) {
        if (this.nodes.size() > 0) {
            final PrimitiveNode node = this.nodes.get(0);
            final List<Attribute> attributes = new ArrayList<>();
            for (GlPrimitive value : node.getValues()) {
                final int componentCount = value.getComponentCount();
                for (int i = 0; i < componentCount / 4 + 1; i++) {
                    final int compCount = (componentCount - 4 * i) % 4;
                    attributes.add(Attribute.ofInstance(attributesIndex + attributes.size(),
                            compCount, value.getDataType(), false));
                }
            }
            return attributes.toArray(new Attribute[0]);
        }
        return new Attribute[0];
    }

    public Model createModel() {

        final Attribute[] vertexAttributes = vertexAttributes(0);
        final Attribute[] nodeAttributes = nodeAttributes(vertexAttributes.length);

        final PrimitiveVertex[] vertices = mesh.getVertices().toArray(new PrimitiveVertex[0]);
        final int[] indices = mesh.getIndices().getIndices().stream().mapToInt(a -> a).toArray();
        final PrimitiveNode[] instances = nodes.toArray(new PrimitiveNode[0]);

        final PrimitiveModelBuffers buffers = new PrimitiveModelBuffers(vertices.length,
                indices.length, instances.length, vertexAttributes, nodeAttributes);

        buffers.load(vertices, indices, instances);

        return buffers.getModel();

    }
}
