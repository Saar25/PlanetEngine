package org.saar.core.common.r3d;

import org.joml.Matrix4fc;
import org.saar.core.model.mesh.writers.MeshIndexWriter;
import org.saar.core.model.mesh.writers.MeshNodeWriter;
import org.saar.core.model.mesh.writers.MeshVertexWriter;

public class Mesh3DWriter implements MeshNodeWriter<Node3D>, MeshVertexWriter<Vertex3D>, MeshIndexWriter {

    private final Mesh3DPrototype prototype;

    public Mesh3DWriter(Mesh3DPrototype prototype) {
        this.prototype = prototype;
    }

    @Override
    public void writeVertex(Vertex3D vertex) {
        this.prototype.getPositionBuffer().getWriter().write(vertex.getPosition3f());
        this.prototype.getNormalBuffer().getWriter().write(vertex.getNormal3f());
        this.prototype.getColourBuffer().getWriter().write(vertex.getColour3f());
    }

    @Override
    public void writeNode(Node3D node) {
        final Matrix4fc matrix = node.getTransform().getTransformationMatrix();
        this.prototype.getTransformBuffer().getWriter().write(matrix);
    }

    @Override
    public void writeIndex(int index) {
        this.prototype.getIndexBuffer().getWriter().write(index);
    }

}
