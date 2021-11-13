package org.saar.core.common.flatreflected;


import org.saar.core.mesh.MeshPrototype;
import org.saar.core.mesh.buffer.MeshIndexBuffer;
import org.saar.core.mesh.buffer.MeshVertexBuffer;

public interface FlatReflectedMeshPrototype extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshIndexBuffer getIndexBuffer();
}
