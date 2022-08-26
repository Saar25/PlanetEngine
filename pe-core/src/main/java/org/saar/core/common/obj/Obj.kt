package org.saar.core.common.obj

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.builder.MeshBufferBuilder
import org.saar.core.mesh.builder.writeIndices
import org.saar.core.mesh.builder.writeVertices
import org.saar.lwjgl.opengl.vbo.VboUsage
import org.saar.lwjgl.util.buffer.FixedBufferBuilder

object Obj {

    @JvmStatic
    fun vertex(position: Vector3fc, uvCoord: Vector2fc, normal: Vector3fc) = object : ObjVertex {
        override val position3f = position
        override val uvCoord2f = uvCoord
        override val normal3f = normal
    }

    @JvmStatic
    fun mesh(vertices: Array<ObjVertex>, indices: IntArray): Mesh {
        val vertexBufferBuilder = MeshBufferBuilder(
            FixedBufferBuilder(vertices.size * 8 * 4),
            VboUsage.STATIC_DRAW)

        val indexBufferBuilder = MeshBufferBuilder(
            FixedBufferBuilder(indices.size * 4),
            VboUsage.STATIC_DRAW)

        val objMeshBuilder = ObjMeshBuilder(indices.size,
            vertexBufferBuilder, vertexBufferBuilder,
            vertexBufferBuilder, indexBufferBuilder)

        objMeshBuilder.writer.writeVertices(vertices)
        objMeshBuilder.writer.writeIndices(indices)

        return objMeshBuilder.load()
    }

    @JvmStatic
    fun mesh(file: String) = ObjMeshLoader(file).use { loader ->
        mesh(loader.loadVertices(), loader.loadIndices())
    }
}