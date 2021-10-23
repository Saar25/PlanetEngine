package org.saar.core.common.obj

import org.saar.core.mesh.builder.ElementsMeshBuilder
import org.saar.core.mesh.builder.MeshBuilder
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.objects.attributes.Attributes

class ObjMeshBuilder private constructor(
    private val builder: ElementsMeshBuilder<ObjVertex>,
    prototype: ObjMeshPrototype
) : MeshBuilder {

    init {
        prototype.positionBuffer.addAttribute(
            Attributes.of(0, 3, DataType.FLOAT, false))
        prototype.uvCoordBuffer.addAttribute(
            Attributes.of(1, 2, DataType.FLOAT, false))
        prototype.normalBuffer.addAttribute(
            Attributes.of(2, 3, DataType.FLOAT, false))
        this.builder.init()
    }

    fun addIndex(index: Int) = this.builder.addIndex(index)

    fun addVertex(vertex: ObjVertex) = this.builder.addVertex(vertex)

    override fun load() = ObjMesh(this.builder.load())

    companion object {
        @JvmStatic
        @JvmOverloads
        fun dynamic(prototype: ObjMeshPrototype = Obj.meshPrototype()) = ObjMeshBuilder(
            ElementsMeshBuilder.Dynamic(prototype, ObjMeshWriter(prototype)), prototype
        )

        @JvmStatic
        @JvmOverloads
        fun fixed(vertices: Int, indices: Int, prototype: ObjMeshPrototype = Obj.meshPrototype()) = ObjMeshBuilder(
            ElementsMeshBuilder.Fixed(vertices, indices, prototype, ObjMeshWriter(prototype)), prototype
        )
    }
}