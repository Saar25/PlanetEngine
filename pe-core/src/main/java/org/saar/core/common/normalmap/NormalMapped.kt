package org.saar.core.common.normalmap

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.builder.MeshBufferBuilder
import org.saar.lwjgl.opengl.vbo.VboUsage
import org.saar.lwjgl.util.buffer.FixedBufferBuilder
import org.saar.maths.utils.Vector3

object NormalMapped {

    @JvmStatic
    fun vertex(position: Vector3fc, uvCoord: Vector2fc, normal: Vector3fc, tangent: Vector3fc, biTangent: Vector3fc) =
        object : NormalMappedVertex {
            override val position3f = position
            override val uvCoord2f = uvCoord
            override val normal3f = normal
            override val tangent3f = tangent
            override val biTangent3f = biTangent
        }

    @JvmStatic
    fun vertex(position: Vector3fc, uvCoord: Vector2fc, normal: Vector3fc, tangent: Vector3fc): NormalMappedVertex {
        val biTangent: Vector3fc = Vector3.cross(normal, tangent)
        return vertex(position, uvCoord, normal, tangent, biTangent)
    }

    @JvmStatic
    fun mesh(vertices: Array<NormalMappedVertex>, indices: IntArray): Mesh {
        val vertexBufferBuilder = MeshBufferBuilder(
            FixedBufferBuilder(vertices.size * 14 * 4),
            VboUsage.STATIC_DRAW)

        val indexBufferBuilder = MeshBufferBuilder(
            FixedBufferBuilder(indices.size * 4),
            VboUsage.STATIC_DRAW)

        val normalMappedMeshBuilder = NormalMappedMeshBuilder(indices.size,
            vertexBufferBuilder, vertexBufferBuilder,
            vertexBufferBuilder, vertexBufferBuilder,
            vertexBufferBuilder, indexBufferBuilder)

        vertices.forEach(normalMappedMeshBuilder.writer::writeVertex)
        indices.forEach(normalMappedMeshBuilder.writer::writeIndex)

        return normalMappedMeshBuilder.load()
    }

    @JvmStatic
    fun mesh(file: String) = NormalMappedMeshLoader(file).use { loader ->
        mesh(loader.loadVertices(), loader.loadIndices())
    }
}