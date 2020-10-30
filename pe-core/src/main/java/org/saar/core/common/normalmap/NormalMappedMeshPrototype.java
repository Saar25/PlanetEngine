package org.saar.core.common.normalmap;


import org.saar.core.mesh.build.MeshPrototype;
import org.saar.core.mesh.build.buffers.MeshIndexBuffer;
import org.saar.core.mesh.build.buffers.MeshVertexBuffer;

public interface NormalMappedMeshPrototype extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getUvCoordBuffer();

    MeshVertexBuffer getNormalBuffer();

    MeshVertexBuffer getTangentBuffer();

    MeshVertexBuffer getBiTangentBuffer();

    MeshIndexBuffer getIndexBuffer();
}
