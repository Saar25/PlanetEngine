package org.saar.core.common.normalmap;


import org.saar.core.mesh.MeshPrototype;
import org.saar.core.mesh.buffer.MeshIndexBuffer;
import org.saar.core.mesh.buffer.MeshVertexBuffer;

public interface NormalMappedMeshPrototype extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getUvCoordBuffer();

    MeshVertexBuffer getNormalBuffer();

    MeshVertexBuffer getTangentBuffer();

    MeshVertexBuffer getBiTangentBuffer();

    MeshIndexBuffer getIndexBuffer();
}
