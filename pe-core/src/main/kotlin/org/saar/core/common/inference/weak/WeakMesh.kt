package org.saar.core.common.inference.weak

import org.saar.core.mesh.DrawCallMesh
import org.saar.core.mesh.Mesh
import org.saar.lwjgl.opengl.attribute.AttributeComposite
import org.saar.lwjgl.opengl.attribute.Attributes.sumBytes
import org.saar.lwjgl.opengl.attribute.IAttribute
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.drawcall.*
import org.saar.lwjgl.opengl.vao.Vao
import org.saar.lwjgl.opengl.vbo.Vbo
import org.saar.lwjgl.opengl.vbo.VboTarget
import org.saar.lwjgl.opengl.vbo.VboUsage
import org.saar.lwjgl.util.buffer.LwjglByteBuffer
import org.saar.rhi.inputassembly.PrimitiveTopology

class WeakMesh private constructor(private val mesh: Mesh) : Mesh {

    override fun draw() = this.mesh.draw()

    override fun delete() = this.mesh.delete()

    companion object {
        @JvmStatic
        fun load(vertices: Array<WeakVertex>, indices: IntArray, instances: Array<WeakInstance>): WeakMesh {
            var vertexAttributes = 0

            val vao = Vao.create()

            if (vertices.isNotEmpty()) {
                val attributes = vertices[0].attributes
                vertexAttributes = attributes.size
                loadVertices(vao, vertices, AttributeComposite(attributes))
            }

            if (instances.isNotEmpty()) {
                val attribute = instances[0].getAttribute(vertexAttributes)
                loadInstances(vao, instances, attribute)
            }

            loadIndices(vao, indices)

            val drawCall: DrawCall = InstancedElementsDrawCall(
                PrimitiveTopology.TRIANGLE_LIST, indices.size, DataType.U_INT, instances.size
            )
            val mesh: Mesh = DrawCallMesh(vao, drawCall)
            return WeakMesh(mesh)
        }

        @JvmStatic
        fun load(vertices: Array<WeakVertex>, instances: Array<WeakInstance>): WeakMesh {
            var vertexAttributes = 0

            val vao = Vao.create()

            if (vertices.isNotEmpty()) {
                val attributes = vertices[0].attributes
                vertexAttributes = attributes.size
                loadVertices(vao, vertices, AttributeComposite(attributes))
            }

            if (instances.isNotEmpty()) {
                val attribute = instances[0].getAttribute(vertexAttributes)
                loadInstances(vao, instances, attribute)
            }

            val drawCall: DrawCall = InstancedArraysDrawCall(
                PrimitiveTopology.TRIANGLE_LIST, vertices.size, instances.size
            )
            val mesh: Mesh = DrawCallMesh(vao, drawCall)
            return WeakMesh(mesh)
        }

        @JvmStatic
        fun load(vertices: Array<WeakVertex>, indices: IntArray): WeakMesh {
            val vao = Vao.create()

            if (vertices.isNotEmpty()) {
                val attributes = vertices[0].attributes
                loadVertices(vao, vertices, AttributeComposite(attributes))
            }

            loadIndices(vao, indices)

            val drawCall = ElementsDrawCall(
                PrimitiveTopology.TRIANGLE_LIST, indices.size, DataType.U_INT
            )
            val mesh: Mesh = DrawCallMesh(vao, drawCall)
            return WeakMesh(mesh)
        }

        @JvmStatic
        fun load(vertices: Array<WeakVertex>): WeakMesh {
            val vao = Vao.create()

            if (vertices.isNotEmpty()) {
                val attributes = vertices[0].attributes
                loadVertices(vao, vertices, AttributeComposite(attributes))
            }

            val drawCall = ArraysDrawCall(
                PrimitiveTopology.TRIANGLE_LIST, vertices.size
            )
            val mesh: Mesh = DrawCallMesh(vao, drawCall)
            return WeakMesh(mesh)
        }

        private fun loadIndices(vao: Vao, indices: IntArray) {
            val vbo = Vbo.create(VboTarget.ELEMENT_ARRAY_BUFFER, VboUsage.STATIC_DRAW)

            val buffer = LwjglByteBuffer.allocate(
                indices.size * DataType.INT.bytes
            )

            for (index in indices) {
                buffer.writer.writeInt(index)
            }

            vbo.allocate(buffer.flip().limit().toLong())
            vbo.store(0, buffer.asByteBuffer())
            vao.loadVbo(vbo)
            vbo.delete()
        }

        private fun loadVertices(vao: Vao, vertices: Array<WeakVertex>, attribute: IAttribute) {
            val vbo = Vbo.create(VboTarget.ARRAY_BUFFER, VboUsage.STATIC_DRAW)

            val buffer = LwjglByteBuffer.allocate(
                vertices.size * sumBytes(attribute)
            )

            for (vertex in vertices) {
                vertex.write(buffer.writer)
            }

            vbo.allocate(buffer.flip().limit().toLong())
            vbo.store(0, buffer.asByteBuffer())
            vao.loadVbo(vbo, attribute)
            vbo.delete()
        }

        private fun loadInstances(vao: Vao, instances: Array<WeakInstance>, attribute: IAttribute) {
            val vbo = Vbo.create(VboTarget.ARRAY_BUFFER, VboUsage.STATIC_DRAW)

            val buffer = LwjglByteBuffer.allocate(
                instances.size * sumBytes(attribute)
            )

            for (instance in instances) {
                instance.write(buffer.writer)
            }

            vbo.allocate(buffer.flip().limit().toLong())
            vbo.store(0, buffer.asByteBuffer())
            vao.loadVbo(vbo, attribute)
            vbo.delete()
        }
    }
}
