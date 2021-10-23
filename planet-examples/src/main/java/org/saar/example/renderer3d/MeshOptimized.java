package org.saar.example.renderer3d;

import org.saar.core.common.r3d.MeshPrototype3D;
import org.saar.core.mesh.buffer.MeshBufferProperty;
import org.saar.core.mesh.buffer.MeshIndexBuffer;
import org.saar.core.mesh.buffer.MeshInstanceBuffer;
import org.saar.core.mesh.buffer.MeshVertexBuffer;

public class MeshOptimized implements MeshPrototype3D {

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
