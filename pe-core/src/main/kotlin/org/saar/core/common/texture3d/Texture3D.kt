package org.saar.core.common.texture3d

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.buffer.DataMeshBufferBuilder
import org.saar.core.mesh.buffer.IndexMeshBufferBuilder
import org.saar.core.mesh.writer.writeIndices
import org.saar.core.mesh.writer.writeVertices
import org.saar.lwjgl.opengl.vbo.VboUsage
import org.saar.lwjgl.util.buffer.FixedBufferBuilder

object Texture3D {

    @JvmStatic
    fun vertex(position: Vector3fc, uvCoord: Vector2fc) = object : Texture3DVertex {
        override val position3f = position
        override val uvCoord2f = uvCoord
    }

    @JvmStatic
    fun mesh(vertices: Array<Texture3DVertex>, indices: IntArray): Mesh {
        val vertexBufferBuilder = DataMeshBufferBuilder(
            FixedBufferBuilder(vertices.size * 5 * 4),
            VboUsage.STATIC_DRAW)

        val indexBufferBuilder = IndexMeshBufferBuilder(
            FixedBufferBuilder(indices.size * 4),
            VboUsage.STATIC_DRAW)

        val texture3DMeshBuilder = Texture3DMeshBuilder(indices.size,
            vertexBufferBuilder, vertexBufferBuilder, indexBufferBuilder)

        texture3DMeshBuilder.writer.writeVertices(vertices)
        texture3DMeshBuilder.writer.writeIndices(indices)

        return texture3DMeshBuilder.load()
    }
}