package org.saar.core.common.smooth;


import org.saar.core.model.mesh.MeshPrototype;
import org.saar.core.model.mesh.buffers.MeshIndexBuffer;
import org.saar.core.model.mesh.buffers.MeshInstanceBuffer;
import org.saar.core.model.mesh.buffers.MeshVertexBuffer;

public interface SmoothMeshPrototype extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getNormalBuffer();

    MeshVertexBuffer getColourBuffer();

    MeshVertexBuffer getTargetBuffer();

    MeshIndexBuffer getIndexBuffer();
}
