package org.saar.core.common.r2d;


import org.saar.core.mesh.build.MeshPrototype;
import org.saar.core.mesh.build.buffers.MeshIndexBuffer;
import org.saar.core.mesh.build.buffers.MeshVertexBuffer;

public interface Mesh2DPrototype extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getColourBuffer();

    MeshIndexBuffer getIndexBuffer();
}
