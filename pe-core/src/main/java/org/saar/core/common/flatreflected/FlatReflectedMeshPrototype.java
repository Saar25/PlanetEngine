package org.saar.core.common.flatreflected;


import org.saar.core.mesh.build.MeshPrototype;
import org.saar.core.mesh.build.buffers.MeshIndexBuffer;
import org.saar.core.mesh.build.buffers.MeshVertexBuffer;

public interface FlatReflectedMeshPrototype extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getUvCoordsBuffer();

    MeshIndexBuffer getIndexBuffer();
}
