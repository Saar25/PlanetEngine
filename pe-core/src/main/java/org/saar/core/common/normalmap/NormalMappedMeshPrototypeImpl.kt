package org.saar.core.common.normalmap

import org.saar.core.model.mesh.MeshBufferProperty
import org.saar.core.model.mesh.buffers.MeshIndexBuffer
import org.saar.core.model.mesh.buffers.MeshVertexBuffer

class NormalMappedMeshPrototypeImpl : NormalMappedMeshPrototype {

    @MeshBufferProperty
    val meshVertexBuffer: MeshVertexBuffer = MeshVertexBuffer.createStatic()

    @MeshBufferProperty
    val meshIndexBuffer: MeshIndexBuffer = MeshIndexBuffer.createStatic()

    override fun getPositionBuffer(): MeshVertexBuffer = this.meshVertexBuffer

    override fun getUvCoordBuffer(): MeshVertexBuffer = this.meshVertexBuffer

    override fun getNormalBuffer(): MeshVertexBuffer = this.meshVertexBuffer

    override fun getTangentBuffer(): MeshVertexBuffer = this.meshVertexBuffer

    override fun getBiTangentBuffer(): MeshVertexBuffer = this.meshVertexBuffer

    override fun getIndexBuffer(): MeshIndexBuffer = this.meshIndexBuffer
}