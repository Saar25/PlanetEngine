package org.saar.core.common.normalmap

import org.saar.core.mesh.builder.ElementsMeshBuilder
import org.saar.core.mesh.builder.MeshBuilder
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.objects.attributes.Attributes

class NormalMappedMeshBuilder private constructor(
    private val builder: ElementsMeshBuilder<NormalMappedVertex>,
    prototype: NormalMappedMeshPrototype
) : MeshBuilder {

    init {
        prototype.positionBuffer.addAttribute(
            Attributes.of(0, 3, DataType.FLOAT, false))
        prototype.uvCoordBuffer.addAttribute(
            Attributes.of(1, 2, DataType.FLOAT, false))
        prototype.normalBuffer.addAttribute(
            Attributes.of(2, 3, DataType.FLOAT, false))
        prototype.tangentBuffer.addAttribute(
            Attributes.of(3, 3, DataType.FLOAT, false))
        prototype.biTangentBuffer.addAttribute(
            Attributes.of(4, 3, DataType.FLOAT, false))
        this.builder.init()
    }

    fun addIndex(index: Int) = this.builder.addIndex(index)

    fun addVertex(vertex: NormalMappedVertex) = this.builder.addVertex(vertex)

    override fun load() = NormalMappedMesh(this.builder.load())

    companion object {
        @JvmStatic
        @JvmOverloads
        fun dynamic(prototype: NormalMappedMeshPrototype = NormalMapped.meshPrototype()) = NormalMappedMeshBuilder(
            ElementsMeshBuilder.Dynamic(prototype, NormalMappedMeshWriter(prototype)), prototype
        )

        @JvmStatic
        @JvmOverloads
        fun fixed(vertices: Int, indices: Int, prototype: NormalMappedMeshPrototype = NormalMapped.meshPrototype()) =
            NormalMappedMeshBuilder(
                ElementsMeshBuilder.Fixed(vertices, indices, prototype, NormalMappedMeshWriter(prototype)), prototype
            )
    }
}