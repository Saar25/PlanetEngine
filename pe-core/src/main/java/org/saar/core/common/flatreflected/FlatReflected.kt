package org.saar.core.common.flatreflected

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.mesh.build.MeshBufferProperty
import org.saar.core.mesh.build.buffers.MeshIndexBuffer
import org.saar.core.mesh.build.buffers.MeshVertexBuffer

object FlatReflected {

    @JvmStatic
    fun vertex(position: Vector3fc, uvCoords: Vector2fc): FlatReflectedVertex {
        return object : FlatReflectedVertex {
            override fun getPosition3f(): Vector3fc = position

            override fun getUvCoords2f(): Vector2fc = uvCoords
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

            override fun getUvCoordsBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getIndexBuffer(): MeshIndexBuffer = this.meshIndexBuffer
        }
    }
}