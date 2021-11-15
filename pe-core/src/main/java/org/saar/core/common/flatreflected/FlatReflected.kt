package org.saar.core.common.flatreflected

import org.joml.Vector3fc
import org.saar.core.mesh.buffer.MeshIndexBuffer
import org.saar.core.mesh.buffer.MeshVertexBuffer

object FlatReflected {

    @JvmStatic
    fun vertex(position: Vector3fc) = object : FlatReflectedVertex {
        override val position3f = position
    }

    @JvmStatic
    fun meshPrototype(): FlatReflectedMeshPrototype {
        val vertexBuffer = MeshVertexBuffer.createStatic()
        val indexBuffer = MeshIndexBuffer.createStatic()
        return FlatReflectedMeshPrototype(vertexBuffer, indexBuffer)
    }

    @JvmStatic
    @JvmOverloads
    fun mesh(
        vertices: Array<FlatReflectedVertex>, indices: IntArray,
        prototype: FlatReflectedMeshPrototype = meshPrototype(),
    ): FlatReflectedMesh {
        return FlatReflectedMeshBuilder.fixed(vertices.size, indices.size, prototype).also {
            vertices.forEach(it::addVertex)
            indices.forEach(it::addIndex)
        }.load()
    }
}