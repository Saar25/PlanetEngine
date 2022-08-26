package org.saar.core.common.r3d

import org.joml.Vector3fc
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.builder.MeshBufferBuilder
import org.saar.core.mesh.builder.writeIndices
import org.saar.core.mesh.builder.writeInstances
import org.saar.core.mesh.builder.writeVertices
import org.saar.lwjgl.opengl.vbo.VboUsage
import org.saar.lwjgl.util.buffer.FixedBufferBuilder
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
    fun mesh(instances: Array<Instance3D>, vertices: Array<Vertex3D>, indices: IntArray): Mesh {
        val vertexBufferBuilder = MeshBufferBuilder(
            FixedBufferBuilder(vertices.size * 9 * 4),
            VboUsage.STATIC_DRAW)

        val instanceBufferBuilder = MeshBufferBuilder(
            FixedBufferBuilder(instances.size * 16 * 4),
            VboUsage.STATIC_DRAW)

        val indexBufferBuilder = MeshBufferBuilder(
            FixedBufferBuilder(indices.size * 4),
            VboUsage.STATIC_DRAW)

        val meshBuilder3D = MeshBuilder3D(indices.size, instances.size,
            vertexBufferBuilder, vertexBufferBuilder, vertexBufferBuilder,
            instanceBufferBuilder, indexBufferBuilder)

        meshBuilder3D.writer.writeVertices(vertices)
        meshBuilder3D.writer.writeInstances(instances)
        meshBuilder3D.writer.writeIndices(indices)

        return meshBuilder3D.load()
    }
}