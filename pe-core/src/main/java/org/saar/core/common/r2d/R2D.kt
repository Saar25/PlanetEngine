package org.saar.core.common.r2d

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.common.r2d.MeshBuilder2D.Companion.fixed
import org.saar.core.mesh.buffer.MeshIndexBuffer
import org.saar.core.mesh.buffer.MeshVertexBuffer

object R2D {

    @JvmStatic
    fun vertex(position: Vector2fc, colour: Vector3fc) = object : Vertex2D {
        override val position2f = position
        override val colour3f = colour
    }

    @JvmStatic
    fun meshPrototype(): MeshPrototype2D {
        val vertex = MeshVertexBuffer.createStatic()
        val index = MeshIndexBuffer.createStatic()
        return MeshPrototype2D(vertex, index)
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