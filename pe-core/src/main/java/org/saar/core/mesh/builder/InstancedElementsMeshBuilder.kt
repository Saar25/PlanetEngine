package org.saar.core.mesh.builder

import org.saar.core.mesh.Instance
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.Meshes
import org.saar.core.mesh.Vertex
import org.saar.core.mesh.prototype.*

abstract class InstancedElementsMeshBuilder<V : Vertex, I : Instance> internal constructor(
    protected val prototype: InstancedIndexedVertexMeshPrototype<V, I>,
) : MeshBuilder {

    protected fun allocate(instances: Int, vertices: Int, indices: Int) {
        this.prototype.allocateVertices(vertices)
        this.prototype.allocateInstances(instances)
        this.prototype.allocateIndices(indices)
    }

    override fun delete() {
        this.prototype.deleteVertices()
        this.prototype.deleteInstances()
        this.prototype.deleteIndices()
    }

    open fun init() = Unit

    abstract fun addInstance(instance: I)
    abstract fun addVertex(vertex: V)
    abstract fun addIndex(index: Int)

    abstract override fun load(): Mesh

    class Dynamic<V : Vertex, I : Instance>(
        prototype: InstancedIndexedVertexMeshPrototype<V, I>,
    ) : InstancedElementsMeshBuilder<V, I>(prototype) {

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

        override fun load(): Mesh {
            allocate(this.instances.size, this.vertices.size, this.indices.size)
            this.prototype.writeInstances(this.instances)
            this.prototype.writeVertices(this.vertices)
            this.prototype.writeIndices(this.indices)
            return Meshes.toInstancedElementsMesh(this.prototype, this.indices.size, this.instances.size)
        }
    }

    class Fixed<V : Vertex, I : Instance>(
        private val instances: Int,
        private val vertices: Int,
        private val indices: Int,
        prototype: InstancedIndexedVertexMeshPrototype<V, I>,
    ) : InstancedElementsMeshBuilder<V, I>(prototype) {

        override fun init() {
            allocate(this.instances, this.vertices, this.indices)
        }

        override fun addInstance(instance: I) = this.prototype.writeInstance(instance)

        override fun addVertex(vertex: V) = this.prototype.writeVertex(vertex)

        override fun addIndex(index: Int) = this.prototype.writeIndex(index)

        override fun load() = Meshes.toInstancedElementsMesh(this.prototype, this.indices, this.instances)
    }
}