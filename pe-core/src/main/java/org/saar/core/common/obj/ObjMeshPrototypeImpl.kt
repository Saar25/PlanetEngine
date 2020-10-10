package org.saar.core.common.obj

import org.saar.core.model.mesh.MeshBufferProperty
import org.saar.core.model.mesh.MeshIndexBuffer
import org.saar.core.model.mesh.MeshVertexBuffer

class ObjMeshPrototypeImpl : ObjMeshPrototype {

    @MeshBufferProperty
    val meshVertexBuffer: MeshVertexBuffer = MeshVertexBuffer.createStatic()

    @MeshBufferProperty
    val meshIndexBuffer: MeshIndexBuffer = MeshIndexBuffer.createStatic()

    override fun getPositionBuffer(): MeshVertexBuffer = this.meshVertexBuffer

    override fun getUvCoordBuffer(): MeshVertexBuffer = this.meshVertexBuffer

    override fun getNormalBuffer(): MeshVertexBuffer = this.meshVertexBuffer

    override fun getIndexBuffer(): MeshIndexBuffer = this.meshIndexBuffer
}