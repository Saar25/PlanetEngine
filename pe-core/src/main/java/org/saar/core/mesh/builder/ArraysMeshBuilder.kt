package org.saar.core.mesh.builder

import org.saar.core.mesh.*
import org.saar.core.mesh.writers.ArraysMeshWriter

abstract class ArraysMeshBuilder<V : Vertex> internal constructor(
    protected val prototype: MeshPrototype,
    protected val writer: ArraysMeshWriter<V>,
) : MeshBuilder {

    protected fun allocate(vertices: Int) {
        with(MeshPrototypeHelper(this.prototype)) {
            allocateVertices(vertices)
        }
    }

    open fun init() = Unit

    abstract fun addVertex(vertex: V)

    abstract override fun load(): ArraysMesh

    class Dynamic<V : Vertex>(
        prototype: MeshPrototype,
        writer: ArraysMeshWriter<V>
    ) : ArraysMeshBuilder<V>(prototype, writer) {

        private val vertices = mutableListOf<V>()

        override fun addVertex(vertex: V) {
            this.vertices += vertex
        }

        override fun load(): ArraysMesh {
            allocate(this.vertices.size)
            this.writer.writeVertices(this.vertices)
            return Meshes.toArraysMesh(this.prototype, this.vertices.size)
        }
    }

    class Fixed<V : Vertex>(
        private val vertices: Int,
        prototype: MeshPrototype,
        writer: ArraysMeshWriter<V>
    ) : ArraysMeshBuilder<V>(prototype, writer) {

        override fun init() {
            allocate(this.vertices)
        }

        override fun addVertex(vertex: V) = this.writer.writeVertex(vertex)

        override fun load() = Meshes.toArraysMesh(this.prototype, this.vertices)
    }
}