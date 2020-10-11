package org.saar.core.common.r2d;


import org.saar.core.model.mesh.buffers.MeshIndexBuffer;
import org.saar.core.model.mesh.MeshPrototype;
import org.saar.core.model.mesh.buffers.MeshVertexBuffer;

public interface Mesh2DPrototype extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getColourBuffer();

    MeshIndexBuffer getIndexBuffer();
}
