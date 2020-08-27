package org.saar.core.common.primitive;

import org.saar.core.model.Model;
import org.saar.core.model.loader.ModelLoader;
import org.saar.core.model.loader.NodeWriter;
import org.saar.core.model.loader.VertexWriter;
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
        if (this.mesh.getVertices().getVertices().size() > 0) {
            final PrimitiveVertex vertex = this.mesh.getVertices().getVertices().get(0);
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

    private static int bytes(Attribute... attributes) {
        int bytes = 0;
        for (Attribute attribute : attributes) {
            bytes += attribute.getBytesPerVertex();
        }
        return bytes;
    }

    public Model createModel() {

        final Attribute[] vertexAttributes = vertexAttributes(0);
        final Attribute[] nodeAttributes = nodeAttributes(vertexAttributes.length);

        final NodeWriter<PrimitiveNode> nodeWriter = new NodeWriter<PrimitiveNode>() {
            @Override public GlPrimitive[] instanceValues(PrimitiveNode node) { return node.getValues(); }

            @Override public Attribute[] instanceAttributes() { return nodeAttributes; }

            @Override public int instanceBytes() { return PrimitiveModelLoader.bytes(nodeAttributes); }
        };

        final VertexWriter<PrimitiveVertex> vertexWriter = new VertexWriter<PrimitiveVertex>() {
            @Override public GlPrimitive[] vertexValues(PrimitiveVertex vertex) { return vertex.getValues(); }

            @Override public Attribute[] vertexAttributes() { return vertexAttributes; }

            @Override public int vertexBytes() { return PrimitiveModelLoader.bytes(vertexAttributes); }
        };

        final ModelLoader<PrimitiveNode, PrimitiveVertex> modelLoader = new ModelLoader<>(nodeWriter, vertexWriter);

        boolean hasIndices = mesh.getIndices().getIndicesArray().length > 0;
        boolean hasInstances = nodes.size() > 0;

        final PrimitiveVertex[] vertices = mesh.getVertices().getVertices().toArray(new PrimitiveVertex[0]);
        final int[] indices = mesh.getIndices().getIndices().stream().mapToInt(a -> a).toArray();
        final PrimitiveNode[] nodeArray = nodes.toArray(new PrimitiveNode[0]);
        if (hasInstances) {
            return hasIndices
                    ? modelLoader.loadInstancedElements(vertices, indices, nodeArray)
                    : modelLoader.loadInstancedArrays(vertices, nodeArray);
        } else {
            return hasIndices
                    ? modelLoader.loadElements(vertices, indices)
                    : modelLoader.loadArrays(vertices);
        }
    }
}
