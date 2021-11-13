package org.saar.core.common.obj

import org.joml.Vector2fc
import org.joml.Vector3fc
import org.saar.core.mesh.buffer.MeshIndexBuffer
import org.saar.core.mesh.buffer.MeshVertexBuffer

object Obj {

    @JvmStatic
    fun vertex(position: Vector3fc, uvCoord: Vector2fc, normal: Vector3fc): ObjVertex {
        return object : ObjVertex {
            override fun getPosition3f(): Vector3fc = position

            override fun getUvCoord2f(): Vector2fc = uvCoord

            override fun getNormal3f(): Vector3fc = normal
        }
    }

    @JvmStatic
    fun meshPrototype(): ObjMeshPrototype {
        val vertex = MeshVertexBuffer.createStatic()
        val index = MeshIndexBuffer.createStatic()
        return ObjMeshPrototype(vertex, index)
    }

    @JvmStatic
    @JvmOverloads
    fun mesh(vertices: Array<ObjVertex>, indices: IntArray, prototype: ObjMeshPrototype = meshPrototype()): ObjMesh {
        return ObjMeshBuilder.fixed(vertices.size, indices.size, prototype).also {
            vertices.forEach(it::addVertex)
            indices.forEach(it::addIndex)
        }.load()
    }

    @JvmStatic
    @JvmOverloads
    fun mesh(file: String, prototype: ObjMeshPrototype = meshPrototype()): ObjMesh {
        return ObjMeshLoader(file).use { loader ->
            mesh(loader.loadVertices(), loader.loadIndices(), prototype)
        }
    }
}