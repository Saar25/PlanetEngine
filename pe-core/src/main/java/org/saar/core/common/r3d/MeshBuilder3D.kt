package org.saar.core.common.r3d

import org.saar.core.mesh.builder.InstancedElementsMeshBuilder
import org.saar.core.mesh.builder.MeshBuilder
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.objects.attributes.Attributes

class MeshBuilder3D private constructor(
    private val builder: InstancedElementsMeshBuilder<Vertex3D, Instance3D>,
    prototype: MeshPrototype3D
) : MeshBuilder {

    init {
        prototype.positionBuffer.addAttribute(
            Attributes.of(0, 3, DataType.FLOAT, true))
        prototype.normalBuffer.addAttribute(
            Attributes.of(1, 3, DataType.FLOAT, true))
        prototype.colourBuffer.addAttribute(
            Attributes.of(2, 3, DataType.FLOAT, true))
        prototype.transformBuffer.addAttributes(
            Attributes.ofInstanced(3, 4, DataType.FLOAT, false),
            Attributes.ofInstanced(4, 4, DataType.FLOAT, false),
            Attributes.ofInstanced(5, 4, DataType.FLOAT, false),
            Attributes.ofInstanced(6, 4, DataType.FLOAT, false)
        )
        this.builder.init()
    }

    fun addIndex(index: Int) = this.builder.addIndex(index)

    fun addVertex(vertex: Vertex3D) = this.builder.addVertex(vertex)

    fun addInstance(instance: Instance3D) = this.builder.addInstance(instance)

    override fun load() = Mesh3D(this.builder.load())

    companion object {
        @JvmStatic
        @JvmOverloads
        fun dynamic(prototype: MeshPrototype3D = R3D.meshPrototype()) = MeshBuilder3D(
            InstancedElementsMeshBuilder.Dynamic(prototype, MeshWriter3D(prototype)), prototype
        )

        @JvmStatic
        @JvmOverloads
        fun fixed(instances: Int, vertices: Int, indices: Int, prototype: MeshPrototype3D = R3D.meshPrototype()) =
            MeshBuilder3D(InstancedElementsMeshBuilder.Fixed(
                instances, vertices, indices, prototype, MeshWriter3D(prototype)), prototype
            )
    }
}