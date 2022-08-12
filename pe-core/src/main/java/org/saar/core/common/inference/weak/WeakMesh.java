package org.saar.core.common.inference.weak;

import org.saar.core.mesh.*;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.drawcall.ArraysDrawCall;
import org.saar.lwjgl.opengl.drawcall.ElementsDrawCall;
import org.saar.lwjgl.opengl.drawcall.InstancedArraysDrawCall;
import org.saar.lwjgl.opengl.drawcall.InstancedElementsDrawCall;
import org.saar.lwjgl.opengl.attribute.Attributes;
import org.saar.lwjgl.opengl.attribute.IAttribute;
import org.saar.lwjgl.opengl.vaos.Vao;
import org.saar.lwjgl.opengl.objects.vbos.Vbo;
import org.saar.lwjgl.opengl.objects.vbos.VboTarget;
import org.saar.lwjgl.opengl.objects.vbos.VboUsage;
import org.saar.lwjgl.util.buffer.LwjglByteBuffer;

public class WeakMesh implements Mesh {

    private final Mesh mesh;

    private WeakMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public static WeakMesh load(WeakVertex[] vertices, int[] indices, WeakInstance[] instances) {
        int vertexAttributes = 0;

        final Vao vao = Vao.create();

        if (vertices.length > 0) {
            final IAttribute[] attributes = vertices[0].getAttributes();
            vertexAttributes = attributes.length;
            loadVertices(vao, vertices, attributes);
        }

        if (instances.length > 0) {
            final IAttribute[] attributes = instances[0].getAttributes(vertexAttributes);
            loadInstances(vao, instances, attributes);
        }

        loadIndices(vao, indices);

        final InstancedElementsDrawCall drawCall = new InstancedElementsDrawCall(
                RenderMode.TRIANGLES, indices.length, DataType.U_INT, instances.length);
        final InstancedElementsMesh mesh = new InstancedElementsMesh(vao, drawCall);
        return new WeakMesh(mesh);
    }

    public static WeakMesh load(WeakVertex[] vertices, WeakInstance[] instances) {
        int vertexAttributes = 0;

        final Vao vao = Vao.create();

        if (vertices.length > 0) {
            final IAttribute[] attributes = vertices[0].getAttributes();
            vertexAttributes = attributes.length;
            loadVertices(vao, vertices, attributes);
        }

        if (instances.length > 0) {
            final IAttribute[] attributes = instances[0].getAttributes(vertexAttributes);
            loadInstances(vao, instances, attributes);
        }

        final InstancedArraysDrawCall drawCall = new InstancedArraysDrawCall(
                RenderMode.TRIANGLES, vertices.length, instances.length);
        final InstancedArraysMesh mesh = new InstancedArraysMesh(vao, drawCall);
        return new WeakMesh(mesh);
    }

    public static WeakMesh load(WeakVertex[] vertices, int[] indices) {
        final Vao vao = Vao.create();

        if (vertices.length > 0) {
            final IAttribute[] attributes = vertices[0].getAttributes();
            loadVertices(vao, vertices, attributes);
        }

        loadIndices(vao, indices);

        final ElementsDrawCall drawCall = new ElementsDrawCall(
                RenderMode.TRIANGLES, indices.length, DataType.U_INT);
        final ElementsMesh mesh = new ElementsMesh(vao, drawCall);
        return new WeakMesh(mesh);
    }

    public static WeakMesh load(WeakVertex[] vertices) {
        final Vao vao = Vao.create();

        if (vertices.length > 0) {
            final IAttribute[] attributes = vertices[0].getAttributes();
            loadVertices(vao, vertices, attributes);
        }

        final ArraysDrawCall drawCall = new ArraysDrawCall(
                RenderMode.TRIANGLES, vertices.length);
        final ArraysMesh mesh = new ArraysMesh(vao, drawCall);
        return new WeakMesh(mesh);
    }

    private static void loadIndices(Vao vao, int[] indices) {
        final Vbo vbo = Vbo.create(VboTarget.ELEMENT_ARRAY_BUFFER, VboUsage.STATIC_DRAW);

        final LwjglByteBuffer buffer = LwjglByteBuffer.allocate(
                indices.length * DataType.INT.getBytes());

        for (int index : indices) {
            buffer.getWriter().writeInt(index);
        }

        vbo.allocate(buffer.flip().limit());
        vbo.store(0, buffer.asByteBuffer());
        vao.loadVbo(vbo);
        vbo.delete();
    }

    private static void loadVertices(Vao vao, WeakVertex[] vertices, IAttribute[] attributes) {
        final Vbo vbo = Vbo.create(VboTarget.ARRAY_BUFFER, VboUsage.STATIC_DRAW);

        final LwjglByteBuffer buffer = LwjglByteBuffer.allocate(
                vertices.length * Attributes.sumBytes(attributes));

        for (WeakVertex vertex : vertices) {
            vertex.write(buffer.getWriter());
        }

        vbo.allocate(buffer.flip().limit());
        vbo.store(0, buffer.asByteBuffer());
        vao.loadVbo(vbo, attributes);
        vbo.delete();
    }

    private static void loadInstances(Vao vao, WeakInstance[] instances, IAttribute[] attributes) {
        final Vbo vbo = Vbo.create(VboTarget.ARRAY_BUFFER, VboUsage.STATIC_DRAW);

        final LwjglByteBuffer buffer = LwjglByteBuffer.allocate(
                instances.length * Attributes.sumBytes(attributes));

        for (WeakInstance instance : instances) {
            instance.write(buffer.getWriter());
        }

        vbo.allocate(buffer.flip().limit());
        vbo.store(0, buffer.asByteBuffer());
        vao.loadVbo(vbo, attributes);
        vbo.delete();
    }

    @Override
    public void draw() {
        this.mesh.draw();
    }

    @Override
    public void delete() {
        this.mesh.delete();
    }
}
