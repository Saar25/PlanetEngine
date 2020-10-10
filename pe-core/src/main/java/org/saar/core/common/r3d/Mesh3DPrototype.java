package org.saar.core.common.r3d;


import org.saar.core.model.mesh.buffers.MeshIndexBuffer;
import org.saar.core.model.mesh.buffers.MeshInstanceBuffer;
import org.saar.core.model.mesh.MeshPrototype;
import org.saar.core.model.mesh.buffers.MeshVertexBuffer;

public interface Mesh3DPrototype extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getNormalBuffer();

    MeshVertexBuffer getColourBuffer();

    MeshInstanceBuffer getTransformBuffer();

    MeshIndexBuffer getIndexBuffer();
}
