package org.saar.core.common.smooth

import org.joml.Vector3fc
import org.saar.core.mesh.build.MeshBufferProperty
import org.saar.core.mesh.build.buffers.MeshIndexBuffer
import org.saar.core.mesh.build.buffers.MeshVertexBuffer

object Smooth {

    @JvmStatic
    fun vertex(position: Vector3fc, normal: Vector3fc, colour: Vector3fc, target: Vector3fc): SmoothVertex {
        return object : SmoothVertex {
            override fun getPosition3f(): Vector3fc = position

            override fun getNormal3f(): Vector3fc = normal

            override fun getColour3f(): Vector3fc = colour

            override fun getTarget3f(): Vector3fc = target
        }
    }

    @JvmStatic
    fun mesh(meshVertexBuffer: MeshVertexBuffer,
             meshIndexBuffer: MeshIndexBuffer): SmoothMeshPrototype {
        return object : SmoothMeshPrototype {

            @MeshBufferProperty
            val meshVertexBuffer: MeshVertexBuffer = meshVertexBuffer

            @MeshBufferProperty
            val meshIndexBuffer: MeshIndexBuffer = meshIndexBuffer

            override fun getPositionBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getNormalBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getColourBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getTargetBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getIndexBuffer(): MeshIndexBuffer = this.meshIndexBuffer
        }
    }

    @JvmStatic
    fun mesh(): SmoothMeshPrototype {
        val vertex = MeshVertexBuffer.createStatic()
        val index = MeshIndexBuffer.createStatic()
        return mesh(vertex, index)
    }

}