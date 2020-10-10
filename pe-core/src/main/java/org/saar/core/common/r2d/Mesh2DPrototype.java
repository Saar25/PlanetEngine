package org.saar.core.common.r2d;


import org.saar.core.model.mesh.MeshIndexBuffer;
import org.saar.core.model.mesh.MeshPrototype;
import org.saar.core.model.mesh.MeshVertexBuffer;

public interface Mesh2DPrototype extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getColourBuffer();

    MeshIndexBuffer getIndexBuffer();
}
