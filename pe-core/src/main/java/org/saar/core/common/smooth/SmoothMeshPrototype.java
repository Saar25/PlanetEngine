package org.saar.core.common.smooth;


import org.saar.core.mesh.build.MeshPrototype;
import org.saar.core.mesh.build.buffers.MeshIndexBuffer;
import org.saar.core.mesh.build.buffers.MeshVertexBuffer;

public interface SmoothMeshPrototype extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getNormalBuffer();

    MeshVertexBuffer getColourBuffer();

    MeshVertexBuffer getTargetBuffer();

    MeshIndexBuffer getIndexBuffer();
}
