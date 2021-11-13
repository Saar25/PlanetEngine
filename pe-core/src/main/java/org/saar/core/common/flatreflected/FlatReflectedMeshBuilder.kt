package org.saar.core.common.flatreflected

import org.saar.core.mesh.builder.ElementsMeshBuilder
import org.saar.core.mesh.builder.MeshBuilder

class FlatReflectedMeshBuilder private constructor(
    private val builder: ElementsMeshBuilder<FlatReflectedVertex>,
) : MeshBuilder {

    fun addIndex(index: Int) = this.builder.addIndex(index)

    fun addVertex(vertex: FlatReflectedVertex) = this.builder.addVertex(vertex)

    override fun load() = FlatReflectedMesh(this.builder.load())

    companion object {
        @JvmStatic
        @JvmOverloads
        fun dynamic(prototype: FlatReflectedMeshPrototype = FlatReflected.meshPrototype()) = FlatReflectedMeshBuilder(
            ElementsMeshBuilder.Dynamic(prototype)
        )

        @JvmStatic
        @JvmOverloads
        fun fixed(vertices: Int, indices: Int, prototype: FlatReflectedMeshPrototype = FlatReflected.meshPrototype()) =
            FlatReflectedMeshBuilder(
                ElementsMeshBuilder.Fixed(vertices, indices, prototype)
            )
    }
}