package org.saar.core.common.r2d

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.model.mesh.MeshBufferProperty
import org.saar.core.model.mesh.buffers.MeshIndexBuffer
import org.saar.core.model.mesh.buffers.MeshVertexBuffer

object R2D {

    @JvmStatic
    fun vertex(position: Vector2fc, colour: Vector3fc): Vertex2D {
        return object : Vertex2D {
            override fun getPosition2f(): Vector2fc = position

            override fun getColour3f(): Vector3fc = colour
        }
    }

    @JvmStatic
    fun mesh(vertex: MeshVertexBuffer, index: MeshIndexBuffer): Mesh2DPrototype {
        return object : Mesh2DPrototype {
            @MeshBufferProperty
            val meshVertexBuffer: MeshVertexBuffer = vertex

            @MeshBufferProperty
            val meshIndexBuffer: MeshIndexBuffer = index

            override fun getPositionBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getColourBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getIndexBuffer(): MeshIndexBuffer = this.meshIndexBuffer
        }
    }

    @JvmStatic
    fun mesh(): Mesh2DPrototype {
        val vertex = MeshVertexBuffer.createStatic()
        val index = MeshIndexBuffer.createStatic()
        return mesh(vertex, index)
    }

}