package org.saar.core.common.obj;


import org.saar.core.model.mesh.MeshIndexBuffer;
import org.saar.core.model.mesh.MeshPrototype;
import org.saar.core.model.mesh.MeshVertexBuffer;

public interface ObjMeshPrototype extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getUvCoordBuffer();

    MeshVertexBuffer getNormalBuffer();

    MeshIndexBuffer getIndexBuffer();
}
