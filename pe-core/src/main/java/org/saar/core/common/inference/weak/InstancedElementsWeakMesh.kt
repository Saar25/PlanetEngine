package org.saar.core.common.inference.weak

import org.saar.core.model.mesh.MeshBufferProperty
import org.saar.core.model.mesh.MeshPrototype
import org.saar.core.model.mesh.buffers.MeshIndexBuffer
import org.saar.core.model.mesh.buffers.MeshInstanceBuffer
import org.saar.core.model.mesh.buffers.MeshVertexBuffer

class InstancedElementsWeakMesh : MeshPrototype {

    @MeshBufferProperty
    val meshVertexBuffer: MeshVertexBuffer = MeshVertexBuffer.createStatic()

    @MeshBufferProperty
    val meshInstanceBuffer: MeshInstanceBuffer = MeshInstanceBuffer.createStatic()

    @MeshBufferProperty
    val meshIndexBuffer: MeshIndexBuffer = MeshIndexBuffer.createStatic()

}