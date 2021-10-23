package org.saar.core.common.flatreflected

import org.joml.Vector3fc
import org.saar.core.mesh.buffer.MeshBufferProperty
import org.saar.core.mesh.buffer.MeshIndexBuffer
import org.saar.core.mesh.buffer.MeshVertexBuffer

object FlatReflected {

    @JvmStatic
    fun vertex(position: Vector3fc): FlatReflectedVertex {
        return FlatReflectedVertex { position }
    }

    @JvmStatic
    fun meshPrototype(): FlatReflectedMeshPrototype {
        return object : FlatReflectedMeshPrototype {
            @MeshBufferProperty
            val meshVertexBuffer: MeshVertexBuffer = MeshVertexBuffer.createStatic()

            @MeshBufferProperty
            val meshIndexBuffer: MeshIndexBuffer = MeshIndexBuffer.createStatic()

            override fun getPositionBuffer(): MeshVertexBuffer = this.meshVertexBuffer

            override fun getIndexBuffer(): MeshIndexBuffer = this.meshIndexBuffer
        }
    }

    @JvmStatic
    @JvmOverloads
    fun mesh(vertices: Array<FlatReflectedVertex>, indices: IntArray,
             prototype: FlatReflectedMeshPrototype = meshPrototype()): FlatReflectedMesh {
        return FlatReflectedMeshBuilder.fixed(vertices.size, indices.size, prototype).also {
            vertices.forEach(it::addVertex)
            indices.forEach(it::addIndex)
        }.load()
    }
}