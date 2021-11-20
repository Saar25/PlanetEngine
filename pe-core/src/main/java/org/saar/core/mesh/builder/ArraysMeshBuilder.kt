package org.saar.core.mesh.builder

import org.saar.core.mesh.ArraysMesh
import org.saar.core.mesh.Meshes
import org.saar.core.mesh.Vertex
import org.saar.core.mesh.prototype.VertexMeshPrototype
import org.saar.core.mesh.prototype.allocateVertices
import org.saar.core.mesh.prototype.writeVertices

abstract class ArraysMeshBuilder<V : Vertex> internal constructor(
    val prototype: VertexMeshPrototype<V>,
) : MeshBuilder {

    protected fun allocate(vertices: Int) {
        this.prototype.allocateVertices(vertices)
    }

    abstract fun addVertex(vertex: V)

    abstract override fun load(): ArraysMesh

    class Dynamic<V : Vertex>(
        prototype: VertexMeshPrototype<V>,
    ) : ArraysMeshBuilder<V>(prototype) {

        private val vertices = mutableListOf<V>()

        override fun addVertex(vertex: V) {
            this.vertices += vertex
        }

        override fun load(): ArraysMesh {
            allocate(this.vertices.size)
            this.prototype.writeVertices(this.vertices)
            return Meshes.toArraysMesh(this.prototype, this.vertices.size)
        }
    }

    class Fixed<V : Vertex>(
        private val vertices: Int,
        prototype: VertexMeshPrototype<V>,
    ) : ArraysMeshBuilder<V>(prototype) {

        init {
            allocate(this.vertices)
        }

        override fun addVertex(vertex: V) = this.prototype.writeVertex(vertex)

        override fun load() = Meshes.toArraysMesh(this.prototype, this.vertices)
    }
}