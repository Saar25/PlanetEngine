package org.saar.core.common.flatreflected

import org.joml.Vector3fc
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.builder.MeshBufferBuilder
import org.saar.core.mesh.builder.writeIndices
import org.saar.core.mesh.builder.writeVertices
import org.saar.lwjgl.opengl.vbo.VboUsage
import org.saar.lwjgl.util.buffer.FixedBufferBuilder

object FlatReflected {

    @JvmStatic
    fun vertex(position: Vector3fc) = object : FlatReflectedVertex {
        override val position3f = position
    }

    @JvmStatic
    fun mesh(vertices: Array<FlatReflectedVertex>, indices: IntArray): Mesh {
        val vertexBufferBuilder = MeshBufferBuilder(
            FixedBufferBuilder(vertices.size * 3 * 4),
            VboUsage.STATIC_DRAW)

        val indexBufferBuilder = MeshBufferBuilder(
            FixedBufferBuilder(indices.size * 4),
            VboUsage.STATIC_DRAW)

        val flatReflectedMeshBuilder = FlatReflectedMeshBuilder(
            indices.size, vertexBufferBuilder, indexBufferBuilder)

        flatReflectedMeshBuilder.writer.writeVertices(vertices)
        flatReflectedMeshBuilder.writer.writeIndices(indices)

        return flatReflectedMeshBuilder.load()
    }
}