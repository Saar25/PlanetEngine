package org.saar.core.common.r2d

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.common.r2d.MeshBuilder2D.Companion.fixed
import org.saar.core.mesh.buffer.MeshBufferProperty
import org.saar.core.mesh.buffer.MeshIndexBuffer
import org.saar.core.mesh.buffer.MeshVertexBuffer

object R2D {

    @JvmStatic
    fun vertex(position: Vector2fc, colour: Vector3fc): Vertex2D {
        return object : Vertex2D {
            override fun getPosition2f(): Vector2fc = position

            override fun getColour3f(): Vector3fc = colour
        }
    }

    @JvmStatic
    fun meshPrototype(vertex: MeshVertexBuffer, index: MeshIndexBuffer): MeshPrototype2D {
        return object : MeshPrototype2D {
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
    fun meshPrototype(): MeshPrototype2D {
        val vertex = MeshVertexBuffer.createStatic()
        val index = MeshIndexBuffer.createStatic()
        return meshPrototype(vertex, index)
    }

    @JvmStatic
    @JvmOverloads
    fun mesh(vertices: Array<Vertex2D>, indices: IntArray, prototype: MeshPrototype2D = meshPrototype()): Mesh2D {
        return fixed(vertices.size, indices.size, prototype).also {
            vertices.forEach(it::addVertex)
            indices.forEach(it::addIndex)
        }.load()
    }
}