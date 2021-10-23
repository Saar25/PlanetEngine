package org.saar.core.common.r3d;


import org.saar.core.mesh.MeshPrototype;
import org.saar.core.mesh.buffer.MeshIndexBuffer;
import org.saar.core.mesh.buffer.MeshInstanceBuffer;
import org.saar.core.mesh.buffer.MeshVertexBuffer;

public interface MeshPrototype3D extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getNormalBuffer();

    MeshVertexBuffer getColourBuffer();

    MeshInstanceBuffer getTransformBuffer();

    MeshIndexBuffer getIndexBuffer();
}
