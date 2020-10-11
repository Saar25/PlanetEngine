package org.saar.example.renderer3d;

import org.saar.core.common.r3d.Mesh3DPrototype;
import org.saar.core.model.mesh.MeshBufferProperty;
import org.saar.core.model.mesh.buffers.MeshIndexBuffer;
import org.saar.core.model.mesh.buffers.MeshInstanceBuffer;
import org.saar.core.model.mesh.buffers.MeshVertexBuffer;

public class MeshOptimized implements Mesh3DPrototype {

    @MeshBufferProperty
    private final MeshVertexBuffer vertexBuffer = MeshVertexBuffer.createStatic();

    @MeshBufferProperty
    private final MeshInstanceBuffer instanceBuffer = MeshInstanceBuffer.createStatic();

    @MeshBufferProperty
    private final MeshIndexBuffer indexBuffer = MeshIndexBuffer.createStatic();

    @Override
    public MeshVertexBuffer getPositionBuffer() {
        return this.vertexBuffer;
    }

    @Override
    public MeshVertexBuffer getNormalBuffer() {
        return this.vertexBuffer;
    }

    @Override
    public MeshVertexBuffer getColourBuffer() {
        return this.vertexBuffer;
    }

    @Override
    public MeshInstanceBuffer getTransformBuffer() {
        return this.instanceBuffer;
    }

    @Override
    public MeshIndexBuffer getIndexBuffer() {
        return this.indexBuffer;
    }
}
