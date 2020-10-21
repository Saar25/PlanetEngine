package org.saar.core.common.inference.weak;

import org.saar.core.model.*;
import org.saar.core.model.mesh.MeshWriters;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.buffers.BufferObjectWrapper;
import org.saar.lwjgl.opengl.objects.vaos.Vao;
import org.saar.lwjgl.opengl.objects.vbos.Vbo;
import org.saar.lwjgl.opengl.objects.vbos.VboTarget;
import org.saar.lwjgl.opengl.objects.vbos.VboUsage;

public class WeakMesh implements Mesh {

    private final Mesh mesh;

    private WeakMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public static WeakMesh load(WeakVertex[] vertices, int[] indices, WeakInstance[] instances) {
        int vertexAttributes = 0;

        final Vao vao = Vao.create();

        if (vertices.length > 0) {
            final Attribute[] attributes = vertices[0].getAttributes();
            vertexAttributes = attributes.length;
            loadVertices(vao, vertices, attributes);
        }

        if (instances.length > 0) {
            final Attribute[] attributes = instances[0].getAttributes(vertexAttributes);
            loadInstances(vao, instances, attributes);
        }

        loadIndices(vao, indices);

        final DrawCall drawCall = new InstancedElementsDrawCall(RenderMode.TRIANGLES,
                indices.length, DataType.U_INT, instances.length);
        final Mesh mesh = new DrawCallMesh(vao, drawCall);
        return new WeakMesh(mesh);
    }

    public static WeakMesh load(WeakVertex[] vertices, WeakInstance[] instances) {
        int vertexAttributes = 0;

        final Vao vao = Vao.create();

        if (vertices.length > 0) {
            final Attribute[] attributes = vertices[0].getAttributes();
            vertexAttributes = attributes.length;
            loadVertices(vao, vertices, attributes);
        }

        if (instances.length > 0) {
            final Attribute[] attributes = instances[0].getAttributes(vertexAttributes);
            loadInstances(vao, instances, attributes);
        }

        final DrawCall drawCall = new InstancedArraysDrawCall(
                RenderMode.TRIANGLES, vertices.length, instances.length);
        final Mesh mesh = new DrawCallMesh(vao, drawCall);
        return new WeakMesh(mesh);
    }

    public static WeakMesh load(WeakVertex[] vertices, int[] indices) {
        final Vao vao = Vao.create();

        if (vertices.length > 0) {
            final Attribute[] attributes = vertices[0].getAttributes();
            loadVertices(vao, vertices, attributes);
        }

        loadIndices(vao, indices);

        final DrawCall drawCall = new ElementsDrawCall(
                RenderMode.TRIANGLES, indices.length, DataType.U_INT);
        final Mesh mesh = new DrawCallMesh(vao, drawCall);
        return new WeakMesh(mesh);
    }

    public static WeakMesh load(WeakVertex[] vertices) {
        final Vao vao = Vao.create();

        if (vertices.length > 0) {
            final Attribute[] attributes = vertices[0].getAttributes();
            loadVertices(vao, vertices, attributes);
        }

        final DrawCall drawCall = new ArraysDrawCall(
                RenderMode.TRIANGLES, vertices.length);
        final Mesh mesh = new DrawCallMesh(vao, drawCall);
        return new WeakMesh(mesh);
    }

    private static void loadIndices(Vao vao, int[] indices) {
        final Vbo vbo = Vbo.create(VboTarget.ELEMENT_ARRAY_BUFFER, VboUsage.STATIC_DRAW);
        final BufferObjectWrapper wrapper = new BufferObjectWrapper(vbo);

        wrapper.allocate(indices.length * DataType.INT.getBytes());
        MeshWriters.writeIndices(wrapper.getWriter()::write, indices);
        wrapper.store(0);
        vao.loadVbo(vbo);
    }

    private static void loadVertices(Vao vao, WeakVertex[] vertices, Attribute[] attributes) {
        final Vbo vbo = Vbo.create(VboTarget.ARRAY_BUFFER, VboUsage.STATIC_DRAW);
        final BufferObjectWrapper wrapper = new BufferObjectWrapper(vbo);

        wrapper.allocate(vertices.length * Attribute.sumBytes(attributes));
        MeshWriters.writeVertices(vertex -> vertex.write(wrapper.getWriter()), vertices);
        wrapper.store(0);

        vao.loadVbo(vbo, attributes);
    }

    private static void loadInstances(Vao vao, WeakInstance[] instances, Attribute[] attributes) {
        final Vbo vbo = Vbo.create(VboTarget.ARRAY_BUFFER, VboUsage.STATIC_DRAW);
        final BufferObjectWrapper wrapper = new BufferObjectWrapper(vbo);

        wrapper.allocate(instances.length * Attribute.sumBytes(attributes));
        MeshWriters.writeNodes(instance -> instance.write(wrapper.getWriter()), instances);
        wrapper.store(0);

        vao.loadVbo(vbo, attributes);
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
