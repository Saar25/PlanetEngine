package org.saar.core.common.primitive;

import org.saar.core.model.*;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.IndexBuffer;
import org.saar.lwjgl.opengl.objects.Vao;
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

    private int getComponentsPerVertex() {
        if (this.mesh.getVertices().getVertices().size() > 0) {
            return getComponentsCount(this.mesh.getVertices().getVertices().get(0).getValues());
        }
        return 0;
    }

    private int getComponentsPerNode() {
        if (this.nodes.size() > 0) {
            return getComponentsCount(this.nodes.get(0).getValues());
        }
        return 0;
    }

    private int getComponentsCount(GlPrimitive... primitives) {
        int componentCount = 0;
        for (GlPrimitive primitive : primitives) {
            componentCount += primitive.getComponentCount();
        }
        return componentCount;
    }

    private int[] writeVertices() {
        int index = 0;
        final int vertexSize = getComponentsPerVertex();
        final int[] buffer = new int[vertexSize * this.mesh.getVertices().count()];

        for (PrimitiveVertex vertex : this.mesh.getVertices().getVertices()) {
            write(index, buffer, vertex.getValues());
            index += vertexSize;
        }
        return buffer;
    }

    private int[] writeNodes() {
        int index = 0;
        final int vertexSize = getComponentsPerNode();
        final int[] buffer = new int[vertexSize * this.nodes.size()];

        for (PrimitiveNode node : this.nodes) {
            write(index, buffer, node.getValues());
            index += vertexSize;
        }
        return buffer;
    }

    private void write(int index, int[] buffer, GlPrimitive... primitives) {
        for (GlPrimitive property : primitives) {
            property.write(index, buffer);
            index += property.getComponentCount();
        }
    }

    public Model createModel() {
        final Vao vao = Vao.create();

        boolean hasIndices = false;
        boolean hasInstances = false;
        int attributesIndex = 0;

        final int[] indexData = mesh.getIndices().getIndicesArray();
        if (indexData.length > 0) {
            final IndexBuffer indexBuffer = new IndexBuffer(VboUsage.STATIC_DRAW);
            indexBuffer.allocateInt(indexData.length);
            indexBuffer.storeData(0, indexData);
            vao.loadIndexBuffer(indexBuffer);
            hasIndices = true;
        }

        final int[] vertexData = writeVertices();
        if (vertexData.length > 0) {
            final DataBuffer vertexBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
            vertexBuffer.allocateInt(vertexData.length);
            vertexBuffer.storeData(0, vertexData);
            final Attribute[] attributes = vertexAttributes(attributesIndex);
            vao.loadDataBuffer(vertexBuffer, attributes);
            attributesIndex += attributes.length;
        }

        final int[] nodeData = writeNodes();
        if (nodeData.length > 0) {
            final DataBuffer nodeBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
            nodeBuffer.allocateInt(nodeData.length);
            nodeBuffer.storeData(0, nodeData);
            vao.loadDataBuffer(nodeBuffer, nodeAttributes(attributesIndex));
            hasInstances = true;
        }

        if (hasInstances) {
            return hasIndices
                    ? new InstancedElementsModel(vao, RenderMode.TRIANGLES, indexData.length, DataType.U_INT, nodes.size())
                    : new InstancedArraysModel(vao, RenderMode.TRIANGLES, this.mesh.getVertices().count(), nodes.size());
        } else {
            return hasIndices
                    ? new ElementsModel(vao, RenderMode.TRIANGLES, indexData.length, DataType.INT)
                    : new ArraysModel(vao, RenderMode.TRIANGLES, this.mesh.getVertices().count());
        }
    }
}
