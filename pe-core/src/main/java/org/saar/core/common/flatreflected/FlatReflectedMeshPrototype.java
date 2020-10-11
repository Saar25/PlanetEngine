package org.saar.core.common.flatreflected;


import org.saar.core.model.mesh.buffers.MeshIndexBuffer;
import org.saar.core.model.mesh.MeshPrototype;
import org.saar.core.model.mesh.buffers.MeshVertexBuffer;

public interface FlatReflectedMeshPrototype extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getNormalBuffer();

    MeshIndexBuffer getIndexBuffer();
}
