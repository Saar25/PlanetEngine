package org.saar.core.common.inference.weak;

import org.saar.core.model.*;
import org.saar.core.model.mesh.MeshPrototypeHelper;
import org.saar.core.model.mesh.MeshWriters;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.RenderMode;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.vaos.Vao;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public class WeakMesh implements Mesh {

    private final Mesh mesh;

    private WeakMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public static WeakMesh load(WeakVertex[] vertices, int[] indices, WeakInstance[] instances) {
        final InstancedElementsWeakMesh prototype = new InstancedElementsWeakMesh();
        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
        int vertexAttributes = 0;

        if (vertices.length > 0) {
            final Attribute[] attributes = vertices[0].getAttributes();
            prototype.getMeshVertexBuffer().addAttributes(attributes);
            vertexAttributes = attributes.length;

            helper.allocateVertices(vertices);
            final BufferWriter vertexWriter = prototype.getMeshVertexBuffer().getWriter();
            MeshWriters.writeVertices((vertex -> vertex.write(vertexWriter)), vertices);
        }

        if (instances.length > 0) {
            final Attribute[] attributes = instances[0].getAttributes(vertexAttributes);
            prototype.getMeshInstanceBuffer().addAttributes(attributes);

            helper.allocateInstances(instances);
            final BufferWriter instanceWriter = prototype.getMeshInstanceBuffer().getWriter();
            MeshWriters.writeNodes((instance -> instance.write(instanceWriter)), instances);
        }

        helper.allocateIndices(indices);
        final BufferWriter indexWriter = prototype.getMeshIndexBuffer().getWriter();
        MeshWriters.writeIndices(indexWriter::write, indices);

        helper.store();

        final Vao vao = Vao.create();
        helper.loadToVao(vao);

        final Mesh mesh = new InstancedElementsMesh(vao,
                RenderMode.TRIANGLES, indices.length, DataType.U_INT, instances.length);
        return new WeakMesh(mesh);
    }

    public static WeakMesh load(WeakVertex[] vertices, WeakInstance[] instances) {
        final InstancedArraysWeakMesh prototype = new InstancedArraysWeakMesh();
        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);
        int vertexAttributes = 0;

        if (vertices.length > 0) {
            final Attribute[] attributes = vertices[0].getAttributes();
            prototype.getMeshVertexBuffer().addAttributes(attributes);
            vertexAttributes = attributes.length;

            helper.allocateVertices(vertices);
            final BufferWriter vertexWriter = prototype.getMeshVertexBuffer().getWriter();
            MeshWriters.writeVertices((vertex -> vertex.write(vertexWriter)), vertices);
        }

        if (instances.length > 0) {
            final Attribute[] attributes = instances[0].getAttributes(vertexAttributes);
            prototype.getMeshInstanceBuffer().addAttributes(attributes);

            helper.allocateInstances(instances);
            final BufferWriter instanceWriter = prototype.getMeshInstanceBuffer().getWriter();
            MeshWriters.writeNodes((instance -> instance.write(instanceWriter)), instances);
        }

        helper.store();

        final Vao vao = Vao.create();
        helper.loadToVao(vao);

        final Mesh mesh = new InstancedArraysMesh(vao,
                RenderMode.TRIANGLES, vertices.length, instances.length);
        return new WeakMesh(mesh);
    }

    public static WeakMesh load(WeakVertex[] vertices, int[] indices) {
        final ElementsWeakMesh prototype = new ElementsWeakMesh();
        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);

        if (vertices.length > 0) {
            prototype.getMeshVertexBuffer().addAttributes(vertices[0].getAttributes());

            helper.allocateVertices(vertices);
            final BufferWriter vertexWriter = prototype.getMeshVertexBuffer().getWriter();
            MeshWriters.writeVertices((vertex -> vertex.write(vertexWriter)), vertices);
        }

        helper.allocateIndices(indices);
        final BufferWriter indexWriter = prototype.getMeshIndexBuffer().getWriter();
        MeshWriters.writeIndices(indexWriter::write, indices);

        helper.store();

        final Vao vao = Vao.create();
        helper.loadToVao(vao);

        final Mesh mesh = new ElementsMesh(vao,
                RenderMode.TRIANGLES, indices.length, DataType.U_INT);
        return new WeakMesh(mesh);
    }

    public static WeakMesh load(WeakVertex[] vertices) {
        final ArraysWeakMesh prototype = new ArraysWeakMesh();
        final MeshPrototypeHelper helper = new MeshPrototypeHelper(prototype);

        if (vertices.length > 0) {
            prototype.getMeshVertexBuffer().addAttributes(vertices[0].getAttributes());

            helper.allocateVertices(vertices);
            final BufferWriter vertexWriter = prototype.getMeshVertexBuffer().getWriter();
            MeshWriters.writeVertices((vertex -> vertex.write(vertexWriter)), vertices);
        }

        helper.store();

        final Vao vao = Vao.create();
        helper.loadToVao(vao);

        final Mesh mesh = new ArraysMesh(vao,
                RenderMode.TRIANGLES, vertices.length);
        return new WeakMesh(mesh);
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
