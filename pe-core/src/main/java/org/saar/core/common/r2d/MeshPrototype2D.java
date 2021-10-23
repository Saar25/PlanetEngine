package org.saar.core.common.r2d;


import org.saar.core.mesh.MeshPrototype;
import org.saar.core.mesh.buffer.MeshIndexBuffer;
import org.saar.core.mesh.buffer.MeshVertexBuffer;

public interface MeshPrototype2D extends MeshPrototype {
    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getColourBuffer();

    MeshIndexBuffer getIndexBuffer();
}
