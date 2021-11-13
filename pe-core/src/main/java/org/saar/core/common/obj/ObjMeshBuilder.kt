package org.saar.core.common.obj

import org.saar.core.mesh.builder.ElementsMeshBuilder
import org.saar.core.mesh.builder.MeshBuilder

class ObjMeshBuilder private constructor(
    private val builder: ElementsMeshBuilder<ObjVertex>,
) : MeshBuilder {

    fun addIndex(index: Int) = this.builder.addIndex(index)

    fun addVertex(vertex: ObjVertex) = this.builder.addVertex(vertex)

    override fun load() = ObjMesh(this.builder.load())

    companion object {
        @JvmStatic
        @JvmOverloads
        fun dynamic(prototype: ObjMeshPrototype = Obj.meshPrototype()) =
            ObjMeshBuilder(ElementsMeshBuilder.Dynamic(prototype))

        @JvmStatic
        @JvmOverloads
        fun fixed(vertices: Int, indices: Int, prototype: ObjMeshPrototype = Obj.meshPrototype()) =
            ObjMeshBuilder(ElementsMeshBuilder.Fixed(vertices, indices, prototype))
    }
}