package org.saar.core.common.flatreflected

import org.joml.Vector3fc
import org.saar.core.model.mesh.MeshBufferProperty
import org.saar.core.model.mesh.buffers.MeshIndexBuffer
import org.saar.core.model.mesh.buffers.MeshVertexBuffer

object FlatReflected {

    @JvmStatic
    fun vertex(position: Vector3fc, normal: Vector3fc): FlatReflectedVertex {
        return object : FlatReflectedVertex {
            override fun getPosition3f(): Vector3fc = position

            override fun getNormal3f(): Vector3fc = normal
        }
    }

    @JvmStatic
    fun meshPrototype(): FlatReflectedMeshPrototype {
        return object : FlatReflectedMeshPrototype {
            @MeshBufferProperty
            val meshVertexBuffer: MeshVertexBuffer = MeshVertexBuffer.createStatic()

            @MeshBufferProperty
            val meshIndexBuffer: MeshIndexBuffer = MeshIndexBuffer.createStatic()

            override fun getPositionBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getNormalBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getIndexBuffer(): MeshIndexBuffer = this.meshIndexBuffer
        }
    }
}