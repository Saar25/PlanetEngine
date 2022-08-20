package org.saar.core.common.obj

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.buffer.MeshIndexBuffer
import org.saar.core.mesh.buffer.MeshVertexBuffer

object Obj {

    @JvmStatic
    fun vertex(position: Vector3fc, uvCoord: Vector2fc, normal: Vector3fc) = object : ObjVertex {
        override val position3f = position
        override val uvCoord2f = uvCoord
        override val normal3f = normal
    }

    @JvmStatic
    fun meshPrototype(): ObjMeshBuffers {
        val vertex = MeshVertexBuffer.createStatic()
        val index = MeshIndexBuffer.createStatic()
        return ObjMeshBuffers(vertex, index)
    }

    @JvmStatic
    @JvmOverloads
    fun mesh(vertices: Array<ObjVertex>, indices: IntArray, prototype: ObjMeshBuffers = meshPrototype()): Mesh {
        return ObjMeshBuilder.fixed(vertices.size, indices.size, prototype).also {
            vertices.forEach(it::addVertex)
            indices.forEach(it::addIndex)
        }.load()
    }

    @JvmStatic
    @JvmOverloads
    fun mesh(file: String, prototype: ObjMeshBuffers = meshPrototype()): Mesh {
        return ObjMeshLoader(file).use { loader ->
            mesh(loader.loadVertices(), loader.loadIndices(), prototype)
        }
    }
}