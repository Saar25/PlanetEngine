package org.saar.core.common.r3d;


import org.saar.core.model.mesh.MeshIndexBuffer;
import org.saar.core.model.mesh.MeshInstanceBuffer;
import org.saar.core.model.mesh.MeshPrototype;
import org.saar.core.model.mesh.MeshVertexBuffer;

public interface Mesh3DPrototype extends MeshPrototype {

    MeshVertexBuffer getPositionBuffer();

    MeshVertexBuffer getNormalBuffer();

    MeshVertexBuffer getColourBuffer();

    MeshInstanceBuffer getTransformBuffer();

    MeshIndexBuffer getIndexBuffer();
}
