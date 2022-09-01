package org.saar.core.common.r2d

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.buffer.DataMeshBufferBuilder
import org.saar.core.mesh.buffer.IndexMeshBufferBuilder
import org.saar.core.mesh.writer.writeIndices
import org.saar.core.mesh.writer.writeVertices
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
        val vertexBufferBuilder = DataMeshBufferBuilder(
            FixedBufferBuilder(vertices.size * 5 * 4),
            VboUsage.STATIC_DRAW)

        val indexBufferBuilder = IndexMeshBufferBuilder(
            FixedBufferBuilder(indices.size * 4),
            VboUsage.STATIC_DRAW)

        val meshBuilder2D = MeshBuilder2D(indices.size,
            vertexBufferBuilder, vertexBufferBuilder, indexBufferBuilder)

        meshBuilder2D.writer.writeVertices(vertices)
        meshBuilder2D.writer.writeIndices(indices)

        return meshBuilder2D.load()
    }
}