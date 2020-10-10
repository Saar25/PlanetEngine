package org.saar.core.common.normalmap;


import org.saar.core.model.mesh.MeshIndexBuffer;
import org.saar.core.model.mesh.MeshVertexBuffer;
import org.saar.core.model.mesh.MeshPrototype;

public interface NormalMappedMeshPrototype extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getUvCoordBuffer();

    MeshVertexBuffer getNormalBuffer();

    MeshVertexBuffer getTangentBuffer();

    MeshVertexBuffer getBiTangentBuffer();

    MeshIndexBuffer getIndexBuffer();
}
