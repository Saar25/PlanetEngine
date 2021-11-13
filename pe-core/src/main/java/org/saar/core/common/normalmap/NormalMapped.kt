package org.saar.core.common.normalmap

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.mesh.buffer.MeshIndexBuffer
import org.saar.core.mesh.buffer.MeshVertexBuffer
import org.saar.maths.utils.Vector3

object NormalMapped {

    @JvmStatic
    fun vertex(
        position: Vector3fc,
        uvCoord: Vector2fc,
        normal: Vector3fc,
        tangent: Vector3fc,
        biTangent: Vector3fc,
    ): NormalMappedVertex {
        return object : NormalMappedVertex {
            override fun getPosition3f(): Vector3fc = position

            override fun getUvCoord2f(): Vector2fc = uvCoord

            override fun getNormal3f(): Vector3fc = normal

            override fun getTangent3f(): Vector3fc = tangent

            override fun getBiTangent3f(): Vector3fc = biTangent
        }
    }

    @JvmStatic
    fun vertex(position: Vector3fc, uvCoord: Vector2fc, normal: Vector3fc, tangent: Vector3fc): NormalMappedVertex {
        val biTangent: Vector3fc = Vector3.cross(normal, tangent)
        return vertex(position, uvCoord, normal, tangent, biTangent)
    }

    @JvmStatic
    fun meshPrototype(): NormalMappedMeshPrototype {
        val vertex = MeshVertexBuffer.createStatic()
        val index = MeshIndexBuffer.createStatic()
        return NormalMappedMeshPrototype(vertex, index)
    }

    @JvmStatic
    @JvmOverloads
    fun mesh(
        vertices: Array<NormalMappedVertex>, indices: IntArray,
        prototype: NormalMappedMeshPrototype = meshPrototype(),
    ): NormalMappedMesh {
        return NormalMappedMeshBuilder.fixed(vertices.size, indices.size, prototype).also {
            vertices.forEach(it::addVertex)
            indices.forEach(it::addIndex)
        }.load()
    }

    @JvmStatic
    @JvmOverloads
    fun mesh(file: String, prototype: NormalMappedMeshPrototype = meshPrototype()): NormalMappedMesh {
        return NormalMappedMeshLoader(file).use { loader ->
            mesh(loader.loadVertices(), loader.loadIndices(), prototype)
        }
    }
}