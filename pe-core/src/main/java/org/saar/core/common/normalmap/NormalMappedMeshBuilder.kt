package org.saar.core.common.normalmap

import org.saar.core.mesh.builder.ElementsMeshBuilder
import org.saar.core.mesh.builder.MeshBuilder

class NormalMappedMeshBuilder private constructor(
    private val builder: ElementsMeshBuilder<NormalMappedVertex>,
) : MeshBuilder {

    override fun delete() = this.builder.delete()

    fun addIndex(index: Int) = this.builder.addIndex(index)

    fun addVertex(vertex: NormalMappedVertex) = this.builder.addVertex(vertex)

    override fun load() = NormalMappedMesh(this.builder.load())

    companion object {
        @JvmStatic
        @JvmOverloads
        fun dynamic(prototype: NormalMappedMeshPrototype = NormalMapped.meshPrototype()) =
            NormalMappedMeshBuilder(ElementsMeshBuilder.Dynamic(prototype))

        @JvmStatic
        @JvmOverloads
        fun fixed(vertices: Int, indices: Int, prototype: NormalMappedMeshPrototype = NormalMapped.meshPrototype()) =
            NormalMappedMeshBuilder(ElementsMeshBuilder.Fixed(vertices, indices, prototype))
    }
}