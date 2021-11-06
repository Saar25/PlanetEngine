package org.saar.core.mesh.builder

import org.saar.core.mesh.*
import org.saar.core.mesh.writers.InstancedElementsMeshWriter

abstract class InstancedElementsMeshBuilder<V : Vertex, I : Instance> internal constructor(
    protected val prototype: MeshPrototype,
    protected val writer: InstancedElementsMeshWriter<V, I>,
) : MeshBuilder {

    protected fun allocate(instances: Int, vertices: Int, indices: Int) {
        with(MeshPrototypeHelper(this.prototype)) {
            allocateInstances(instances)
            allocateVertices(vertices)
            allocateIndices(indices)
        }
    }

    open fun init() = Unit

    abstract fun addInstance(instance: I)
    abstract fun addVertex(vertex: V)
    abstract fun addIndex(index: Int)

    abstract override fun load(): InstancedElementsMesh

    class Dynamic<V : Vertex, I : Instance>(
        prototype: MeshPrototype,
        writer: InstancedElementsMeshWriter<V, I>
    ) : InstancedElementsMeshBuilder<V, I>(prototype, writer) {

        private val instances = mutableListOf<I>()
        private val vertices = mutableListOf<V>()
        private val indices = mutableListOf<Int>()

        override fun addInstance(instance: I) {
            this.instances += instance
        }

        override fun addVertex(vertex: V) {
            this.vertices += vertex
        }

        override fun addIndex(index: Int) {
            this.indices += index
        }

        override fun load(): InstancedElementsMesh {
            allocate(this.instances.size, this.vertices.size, this.indices.size)
            this.writer.writeInstances(this.instances)
            this.writer.writeVertices(this.vertices)
            this.writer.writeIndices(this.indices)
            return Meshes.toInstancedElementsMesh(this.prototype, this.indices.size, this.instances.size)
        }
    }

    class Fixed<V : Vertex, I : Instance>(
        private val instances: Int,
        private val vertices: Int,
        private val indices: Int,
        prototype: MeshPrototype,
        writer: InstancedElementsMeshWriter<V, I>
    ) : InstancedElementsMeshBuilder<V, I>(prototype, writer) {

        override fun init() {
            allocate(this.instances, this.vertices, this.indices)
        }

        override fun addInstance(instance: I) = this.writer.writeInstance(instance)

        override fun addVertex(vertex: V) = this.writer.writeVertex(vertex)

        override fun addIndex(index: Int) = this.writer.writeIndex(index)

        override fun load() = Meshes.toInstancedElementsMesh(this.prototype, this.indices, this.instances)
    }
}