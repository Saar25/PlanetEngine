package org.saar.core.common.r3d;

import org.joml.Matrix4fc;
import org.saar.core.mesh.build.writers.MeshIndexWriter;
import org.saar.core.mesh.build.writers.MeshInstanceWriter;
import org.saar.core.mesh.build.writers.MeshVertexWriter;

public class Mesh3DWriter implements MeshInstanceWriter<Instance3D>, MeshVertexWriter<Vertex3D>, MeshIndexWriter {

    private final Mesh3DPrototype prototype;

    public Mesh3DWriter(Mesh3DPrototype prototype) {
        this.prototype = prototype;
    }

    @Override
    public void writeVertex(Vertex3D vertex) {
        this.prototype.getPositionBuffer().getWriter().write3f(vertex.getPosition3f());
        this.prototype.getNormalBuffer().getWriter().write3f(vertex.getNormal3f());
        this.prototype.getColourBuffer().getWriter().write3f(vertex.getColour3f());
    }

    @Override
    public void writeInstance(Instance3D instance) {
        final Matrix4fc matrix = instance.getTransform().getTransformationMatrix();
        this.prototype.getTransformBuffer().getWriter().write4x4f(matrix);
    }

    @Override
    public void writeIndex(int index) {
        this.prototype.getIndexBuffer().getWriter().writeInt(index);
    }

}
