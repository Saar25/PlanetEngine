package org.saar.core.model.loader;

import org.saar.core.model.*;
import org.saar.core.node.Node;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.IndexBuffer;
import org.saar.lwjgl.opengl.objects.Vao;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;
import org.saar.lwjgl.opengl.utils.BufferWriter;
import org.saar.lwjgl.opengl.utils.MemoryUtils;

import java.nio.ByteBuffer;

public class ModelLoader<N extends Node, V extends Vertex> {

    private final NodeWriter<N> nodeWriter;
    private final VertexWriter<V> vertexWriter;

    public ModelLoader(NodeWriter<N> nodeWriter, VertexWriter<V> vertexWriter) {
        this.nodeWriter = nodeWriter;
        this.vertexWriter = vertexWriter;
    }

    private DataBuffer toDataBuffer(ByteBuffer buffer) {
        final DataBuffer vbo = new DataBuffer(VboUsage.STATIC_DRAW);
        vbo.allocateByte(buffer.flip().capacity());
        vbo.storeData(0, buffer);
        return vbo;
    }

    private static IndexBuffer toIndexVbo(int[] indices) {
        final IndexBuffer vbo = new IndexBuffer(VboUsage.STATIC_DRAW);
        vbo.allocateInt(indices.length);
        vbo.storeData(0, indices);
        return vbo;
    }

    private DataBuffer toVerticesVbo(V[] vertices) {
        final int bufferSize = vertexWriter.vertexBytes() * vertices.length;
        final ByteBuffer buffer = MemoryUtils.allocByte(bufferSize);
        final BufferWriter bufferWriter = new BufferWriter(buffer);

        for (V vertex : vertices) {
            final GlPrimitive[] primitives = vertexWriter.vertexValues(vertex);
            for (GlPrimitive primitive : primitives) {
                primitive.write(bufferWriter);
            }
        }
        return toDataBuffer(buffer);
    }

    private DataBuffer toNodesVbo(N[] nodes) {
        final int bufferSize = nodeWriter.instanceBytes() * nodes.length;
        final ByteBuffer buffer = MemoryUtils.allocByte(bufferSize);
        final BufferWriter bufferWriter = new BufferWriter(buffer);
        for (N node : nodes) {
            final GlPrimitive[] primitives = nodeWriter.instanceValues(node);
            for (GlPrimitive primitive : primitives) {
                primitive.write(bufferWriter);
            }
        }
        return toDataBuffer(buffer);
    }

    public InstancedElementsModel loadInstancedElements(V[] vertices, int[] indices, N[] nodes) {
        final Vao vao = Vao.create();
        vao.loadIndexBuffer(toIndexVbo(indices));
        vao.loadDataBuffer(toVerticesVbo(vertices), vertexWriter.vertexAttributes());
        vao.loadDataBuffer(toNodesVbo(nodes), nodeWriter.instanceAttributes());

        return new InstancedElementsModel(vao, RenderMode.TRIANGLES, indices.length, DataType.U_INT, nodes.length);
    }

    public InstancedArraysModel loadInstancedArrays(V[] vertices, N[] nodes) {
        final Vao vao = Vao.create();
        vao.loadDataBuffer(toVerticesVbo(vertices), vertexWriter.vertexAttributes());
        vao.loadDataBuffer(toNodesVbo(nodes), nodeWriter.instanceAttributes());

        return new InstancedArraysModel(vao, RenderMode.TRIANGLES, vertices.length, nodes.length);
    }

    public ElementsModel loadElements(V[] vertices, int[] indices) {
        final Vao vao = Vao.create();
        vao.loadIndexBuffer(toIndexVbo(indices));
        vao.loadDataBuffer(toVerticesVbo(vertices), vertexWriter.vertexAttributes());

        return new ElementsModel(vao, RenderMode.TRIANGLES, indices.length, DataType.INT);
    }

    public ArraysModel loadArrays(V[] vertices) {
        final Vao vao = Vao.create();
        vao.loadDataBuffer(toVerticesVbo(vertices), vertexWriter.vertexAttributes());

        return new ArraysModel(vao, RenderMode.TRIANGLES, vertices.length);
    }
}
