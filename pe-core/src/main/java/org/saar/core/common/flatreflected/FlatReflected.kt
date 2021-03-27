package org.saar.core.common.flatreflected

import org.joml.Vector3fc
import org.saar.core.mesh.build.MeshBufferProperty
import org.saar.core.mesh.build.buffers.MeshIndexBuffer
import org.saar.core.mesh.build.buffers.MeshVertexBuffer

object FlatReflected {

    @JvmStatic
    fun vertex(position: Vector3fc): FlatReflectedVertex {
        return FlatReflectedVertex { position }
    }

    @JvmStatic
    fun meshPrototype(): FlatReflectedMeshPrototype {
        return object : FlatReflectedMeshPrototype {
            @MeshBufferProperty
            val meshVertexBuffer: MeshVertexBuffer = MeshVertexBuffer.createStatic()

            @MeshBufferProperty
            val meshIndexBuffer: MeshIndexBuffer = MeshIndexBuffer.createStatic()

            override fun getPositionBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getIndexBuffer(): MeshIndexBuffer = this.meshIndexBuffer
        }
    }
}