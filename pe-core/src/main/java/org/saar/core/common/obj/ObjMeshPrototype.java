package org.saar.core.common.obj;


import org.saar.core.mesh.build.MeshPrototype;
import org.saar.core.mesh.build.buffers.MeshIndexBuffer;
import org.saar.core.mesh.build.buffers.MeshVertexBuffer;

public interface ObjMeshPrototype extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getUvCoordBuffer();

    MeshVertexBuffer getNormalBuffer();

    MeshIndexBuffer getIndexBuffer();
}
