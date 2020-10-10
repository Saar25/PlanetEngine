package org.saar.core.common.r3d

import org.saar.core.model.mesh.MeshBufferProperty
import org.saar.core.model.mesh.buffers.MeshIndexBuffer
import org.saar.core.model.mesh.buffers.MeshInstanceBuffer
import org.saar.core.model.mesh.buffers.MeshVertexBuffer

class Mesh3DPrototypeImpl : Mesh3DPrototype {

    @MeshBufferProperty
    val meshVertexBuffer: MeshVertexBuffer = MeshVertexBuffer.createStatic()

    @MeshBufferProperty
    val meshInstanceBuffer: MeshInstanceBuffer = MeshInstanceBuffer.createStatic()

    @MeshBufferProperty
    val meshIndexBuffer: MeshIndexBuffer = MeshIndexBuffer.createStatic()

    override fun getPositionBuffer(): MeshVertexBuffer = this.meshVertexBuffer

    override fun getNormalBuffer(): MeshVertexBuffer = this.meshVertexBuffer

    override fun getColourBuffer(): MeshVertexBuffer = this.meshVertexBuffer

    override fun getTransformBuffer(): MeshInstanceBuffer = this.meshInstanceBuffer

    override fun getIndexBuffer(): MeshIndexBuffer = this.meshIndexBuffer
}