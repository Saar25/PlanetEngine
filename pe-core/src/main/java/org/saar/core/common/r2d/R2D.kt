package org.saar.core.common.r2d

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.builder.MeshBufferBuilder
import org.saar.lwjgl.opengl.vbo.VboUsage
import org.saar.lwjgl.util.buffer.FixedBufferBuilder

object R2D {

    @JvmStatic
    fun vertex(position: Vector2fc, colour: Vector3fc) = object : Vertex2D {
        override val position2f = position
        override val colour3f = colour
    }

    @JvmStatic
    fun mesh(vertices: Array<Vertex2D>, indices: IntArray): Mesh {
        val vertexBufferBuilder = MeshBufferBuilder(
            FixedBufferBuilder(vertices.size * 5 * 4),
            VboUsage.STATIC_DRAW)

        val indexBufferBuilder = MeshBufferBuilder(
            FixedBufferBuilder(indices.size * 4),
            VboUsage.STATIC_DRAW)

        val meshBuilder2D = MeshBuilder2D(indices.size,
            vertexBufferBuilder, vertexBufferBuilder, indexBufferBuilder)

        vertices.forEach(meshBuilder2D.writer::writeVertex)
        indices.forEach(meshBuilder2D.writer::writeIndex)

        return meshBuilder2D.load()
    }
}