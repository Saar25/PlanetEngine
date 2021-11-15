package org.saar.core.common.r3d

import org.joml.Vector3fc
import org.saar.core.mesh.buffer.MeshIndexBuffer
import org.saar.core.mesh.buffer.MeshInstanceBuffer
import org.saar.core.mesh.buffer.MeshVertexBuffer
import org.saar.maths.transform.SimpleTransform
import org.saar.maths.transform.Transform

object R3D {

    @JvmStatic
    fun instance(transform: Transform) = object : Instance3D {
        override val transform = transform
    }

    @JvmStatic
    fun instance() = instance(SimpleTransform())

    @JvmStatic
    fun vertex(position: Vector3fc, normal: Vector3fc, colour: Vector3fc) = object : Vertex3D {
        override val position3f = position
        override val normal3f = normal
        override val colour3f = colour
    }

    @JvmStatic
    fun meshPrototype(): MeshPrototype3D {
        val instance = MeshInstanceBuffer.createStatic()
        val vertex = MeshVertexBuffer.createStatic()
        val index = MeshIndexBuffer.createStatic()
        return MeshPrototype3D(vertex, instance, index)
    }

    @JvmStatic
    @JvmOverloads
    fun mesh(
        instances: Array<Instance3D>, vertices: Array<Vertex3D>,
        indices: IntArray, prototype: MeshPrototype3D = meshPrototype(),
    ): Mesh3D {
        return MeshBuilder3D.fixed(instances.size, vertices.size, indices.size, prototype).also {
            instances.forEach(it::addInstance)
            vertices.forEach(it::addVertex)
            indices.forEach(it::addIndex)
        }.load()
    }
}