package org.saar.core.common.r3d;


import org.saar.core.mesh.build.MeshPrototype;
import org.saar.core.mesh.build.buffers.MeshIndexBuffer;
import org.saar.core.mesh.build.buffers.MeshInstanceBuffer;
import org.saar.core.mesh.build.buffers.MeshVertexBuffer;

public interface Mesh3DPrototype extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getNormalBuffer();

    MeshVertexBuffer getColourBuffer();

    MeshInstanceBuffer getTransformBuffer();

    MeshIndexBuffer getIndexBuffer();
}
