package org.saar.core.common.r3d

import org.saar.core.mesh.builder.InstancedElementsMeshBuilder
import org.saar.core.mesh.builder.MeshBuilder

class MeshBuilder3D private constructor(
    private val builder: InstancedElementsMeshBuilder<Vertex3D, Instance3D>,
) : MeshBuilder {

    init {
        this.builder.init()
    }

    fun addIndex(index: Int) = this.builder.addIndex(index)

    fun addVertex(vertex: Vertex3D) = this.builder.addVertex(vertex)

    fun addInstance(instance: Instance3D) = this.builder.addInstance(instance)

    override fun load() = Mesh3D(this.builder.load())

    companion object {
        @JvmStatic
        @JvmOverloads
        fun dynamic(prototype: MeshPrototype3D = R3D.meshPrototype()) =
            MeshBuilder3D(InstancedElementsMeshBuilder.Dynamic(prototype))

        @JvmStatic
        @JvmOverloads
        fun fixed(instances: Int, vertices: Int, indices: Int, prototype: MeshPrototype3D = R3D.meshPrototype()) =
            MeshBuilder3D(InstancedElementsMeshBuilder.Fixed(instances, vertices, indices, prototype))
    }
}