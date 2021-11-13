package org.saar.core.common.r3d

import org.joml.Vector3fc
import org.saar.core.mesh.buffer.MeshIndexBuffer
import org.saar.core.mesh.buffer.MeshInstanceBuffer
import org.saar.core.mesh.buffer.MeshVertexBuffer
import org.saar.maths.transform.SimpleTransform
import org.saar.maths.transform.Transform

object R3D {

    @JvmStatic
    fun instance(transform: Transform): Instance3D {
        return Instance3D { transform }
    }

    @JvmStatic
    fun instance(): Instance3D {
        return instance(SimpleTransform())
    }

    @JvmStatic
    fun vertex(position: Vector3fc, normal: Vector3fc, colour: Vector3fc): Vertex3D {
        return object : Vertex3D {
            override fun getPosition3f(): Vector3fc = position

            override fun getNormal3f(): Vector3fc = normal

            override fun getColour3f(): Vector3fc = colour
        }
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