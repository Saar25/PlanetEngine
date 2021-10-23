package org.saar.example.renderer3d;

import org.saar.core.common.r3d.MeshPrototype3D;
import org.saar.core.mesh.buffer.MeshBufferProperty;
import org.saar.core.mesh.buffer.MeshIndexBuffer;
import org.saar.core.mesh.buffer.MeshInstanceBuffer;
import org.saar.core.mesh.buffer.MeshVertexBuffer;

public class MeshUnoptimized implements MeshPrototype3D {

    @MeshBufferProperty
    private final MeshVertexBuffer positionBuffer = MeshVertexBuffer.createStatic();

    @MeshBufferProperty
    private final MeshVertexBuffer normalBuffer = MeshVertexBuffer.createStatic();

    @MeshBufferProperty
    private final MeshVertexBuffer colourBuffer = MeshVertexBuffer.createStatic();

    @MeshBufferProperty
    private final MeshInstanceBuffer instanceBuffer = MeshInstanceBuffer.createStatic();

    @MeshBufferProperty
    private final MeshIndexBuffer indexBuffer = MeshIndexBuffer.createStatic();

    @Override
    public MeshVertexBuffer getPositionBuffer() {
        return this.positionBuffer;
    }

    @Override
    public MeshVertexBuffer getNormalBuffer() {
        return this.normalBuffer;
    }

    @Override
    public MeshVertexBuffer getColourBuffer() {
        return this.colourBuffer;
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
