package org.saar.core.mesh.builder

import org.saar.core.mesh.*
import org.saar.core.mesh.writers.ElementsMeshWriter

abstract class ElementsMeshBuilder<V : Vertex> internal constructor(
    val prototype: MeshPrototype,
    val writer: ElementsMeshWriter<V>,
) : MeshBuilder {

    protected fun allocate(vertices: Int, indices: Int) {
        with(MeshPrototypeHelper(this.prototype)) {
            allocateVertices(vertices)
            allocateIndices(indices)
        }
    }

    open fun init() = Unit

    abstract fun addVertex(vertex: V)
    abstract fun addIndex(index: Int)

    class Dynamic<V : Vertex>(
        prototype: MeshPrototype,
        writer: ElementsMeshWriter<V>
    ) : ElementsMeshBuilder<V>(prototype, writer) {

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
            this.writer.writeVertices(this.vertices)
            this.writer.writeIndices(this.indices)
            return Meshes.toElementsMesh(this.prototype, this.indices.size)
        }
    }

    class Fixed<V : Vertex>(
        private val vertices: Int,
        private val indices: Int,
        prototype: MeshPrototype,
        writer: ElementsMeshWriter<V>
    ) : ElementsMeshBuilder<V>(prototype, writer) {

        override fun init() {
            allocate(this.vertices, this.indices)
        }

        override fun addVertex(vertex: V) = this.writer.writeVertex(vertex)

        override fun addIndex(index: Int) = this.writer.writeIndex(index)

        override fun load() = Meshes.toElementsMesh(this.prototype, this.indices)
    }
}