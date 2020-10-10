package org.saar.core.common.r2d

import org.saar.core.model.mesh.MeshBufferProperty
import org.saar.core.model.mesh.MeshIndexBuffer
import org.saar.core.model.mesh.MeshVertexBuffer

class Mesh2DPrototypeImpl : Mesh2DPrototype {

    @MeshBufferProperty
    val meshVertexBuffer: MeshVertexBuffer = MeshVertexBuffer.createStatic()

    @MeshBufferProperty
    val meshIndexBuffer: MeshIndexBuffer = MeshIndexBuffer.createStatic()

    override fun getPositionBuffer(): MeshVertexBuffer = this.meshVertexBuffer

    override fun getColourBuffer(): MeshVertexBuffer = this.meshVertexBuffer

    override fun getIndexBuffer(): MeshIndexBuffer = this.meshIndexBuffer
}