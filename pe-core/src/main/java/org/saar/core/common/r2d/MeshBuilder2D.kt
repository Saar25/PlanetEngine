package org.saar.core.common.r2d

import org.saar.core.mesh.builder.ElementsMeshBuilder
import org.saar.core.mesh.builder.MeshBuilder

class MeshBuilder2D private constructor(
    private val builder: ElementsMeshBuilder<Vertex2D>,
) : MeshBuilder {

    fun addIndex(index: Int) = this.builder.addIndex(index)

    fun addVertex(vertex: Vertex2D) = this.builder.addVertex(vertex)

    override fun load() = Mesh2D(this.builder.load())

    companion object {
        @JvmStatic
        @JvmOverloads
        fun dynamic(prototype: MeshPrototype2D = R2D.meshPrototype()) = MeshBuilder2D(
            ElementsMeshBuilder.Dynamic(prototype)
        )

        @JvmStatic
        @JvmOverloads
        fun fixed(vertices: Int, indices: Int, prototype: MeshPrototype2D = R2D.meshPrototype()) = MeshBuilder2D(
            ElementsMeshBuilder.Fixed(vertices, indices, prototype)
        )
    }
}