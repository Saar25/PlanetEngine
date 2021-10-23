package org.saar.core.common.r3d;

import org.joml.Matrix4fc;
import org.saar.core.mesh.writers.InstancedElementsMeshWriter;

public class MeshWriter3D implements InstancedElementsMeshWriter<Vertex3D, Instance3D> {

    private final MeshPrototype3D prototype;

    public MeshWriter3D(MeshPrototype3D prototype) {
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
