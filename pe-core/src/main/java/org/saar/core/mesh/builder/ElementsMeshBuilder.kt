package org.saar.core.mesh.builder

import org.saar.core.mesh.Mesh
import org.saar.core.mesh.Meshes
import org.saar.core.mesh.Vertex
import org.saar.core.mesh.prototype.*

abstract class ElementsMeshBuilder<V : Vertex> internal constructor(
    val prototype: IndexedVertexMeshPrototype<V>,
) : MeshBuilder {

    protected fun allocate(vertices: Int, indices: Int) {
        this.prototype.allocateVertices(vertices)
        this.prototype.allocateIndices(indices)
    }

    override fun delete() {
        this.prototype.deleteVertices()
        this.prototype.deleteIndices()
    }

    abstract fun addVertex(vertex: V)
    abstract fun addIndex(index: Int)

    abstract override fun load(): Mesh

    class Dynamic<V : Vertex>(
        prototype: IndexedVertexMeshPrototype<V>,
    ) : ElementsMeshBuilder<V>(prototype) {

        private val vertices = mutableListOf<V>()
        private val indices = mutableListOf<Int>()

        override fun addVertex(vertex: V) {
            this.vertices += vertex
        }

        override fun addIndex(index: Int) {
            this.indices += index
        }

        override fun load(): Mesh {
            allocate(this.vertices.size, this.indices.size)
            this.prototype.writeVertices(this.vertices)
            this.prototype.writeIndices(this.indices)
            return Meshes.toElementsMesh(this.prototype, this.indices.size)
        }
    }

    class Fixed<V : Vertex>(
        private val vertices: Int,
        private val indices: Int,
        prototype: IndexedVertexMeshPrototype<V>,
    ) : ElementsMeshBuilder<V>(prototype) {

        init {
            allocate(this.vertices, this.indices)
        }

        override fun addVertex(vertex: V) = this.prototype.writeVertex(vertex)

        override fun addIndex(index: Int) = this.prototype.writeIndex(index)

        override fun load() = Meshes.toElementsMesh(this.prototype, this.indices)
    }
}