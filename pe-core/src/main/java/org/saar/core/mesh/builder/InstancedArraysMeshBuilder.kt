package org.saar.core.mesh.builder

import org.saar.core.mesh.*
import org.saar.core.mesh.writers.InstancedArraysMeshWriter
import org.saar.lwjgl.opengl.constants.RenderMode

abstract class InstancedArraysMeshBuilder<V : Vertex, I : Instance> internal constructor(
    protected val prototype: MeshPrototype,
    protected val writer: InstancedArraysMeshWriter<V, I>,
) : MeshBuilder {

    protected fun allocate(instances: Int, vertices: Int) {
        with(MeshPrototypeHelper(this.prototype)) {
            allocateInstances(instances)
            allocateVertices(vertices)
        }
    }

    open fun init() = Unit

    abstract fun addInstance(instance: I)
    abstract fun addVertex(vertex: V)

    abstract fun load(renderMode: RenderMode): InstancedArraysMesh

    override fun load() = load(RenderMode.TRIANGLES)

    class Dynamic<V : Vertex, I : Instance>(
        prototype: MeshPrototype,
        writer: InstancedArraysMeshWriter<V, I>
    ) : InstancedArraysMeshBuilder<V, I>(prototype, writer) {

        private val instances = mutableListOf<I>()
        private val vertices = mutableListOf<V>()

        override fun addInstance(instance: I) {
            this.instances += instance
        }

        override fun addVertex(vertex: V) {
            this.vertices += vertex
        }

        override fun load(renderMode: RenderMode): InstancedArraysMesh {
            allocate(this.instances.size, this.vertices.size)
            this.writer.writeInstances(this.instances)
            this.writer.writeVertices(this.vertices)
            return Meshes.toInstancedArrayMesh(this.prototype,
                this.vertices.size, this.instances.size, renderMode)
        }
    }

    class Fixed<V : Vertex, I : Instance>(
        private val instances: Int,
        private val vertices: Int,
        prototype: MeshPrototype,
        writer: InstancedArraysMeshWriter<V, I>
    ) : InstancedArraysMeshBuilder<V, I>(prototype, writer) {

        override fun init() {
            allocate(this.instances, this.vertices)
        }

        override fun addInstance(instance: I) = this.writer.writeInstance(instance)

        override fun addVertex(vertex: V) = this.writer.writeVertex(vertex)

        override fun load(renderMode: RenderMode) = Meshes.toInstancedArrayMesh(
            this.prototype, this.vertices, this.instances, renderMode)
    }
}