package org.saar.core.common.particles

import org.saar.core.mesh.builder.InstancedArraysMeshBuilder
import org.saar.core.mesh.builder.MeshBuilder
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.objects.attributes.Attributes

class ParticlesMeshBuilder private constructor(
    private val builder: InstancedArraysMeshBuilder<ParticlesVertex, ParticlesInstance>,
    prototype: ParticlesMeshPrototype
) : MeshBuilder {

    init {
        prototype.positionBuffer.addAttribute(
            Attributes.ofInstanced(0, 3, DataType.FLOAT, false))
        prototype.ageBuffer.addAttribute(
            Attributes.ofInstanced(1, 1, DataType.FLOAT, false))
        this.builder.init()
    }

    fun addInstance(instance: ParticlesInstance) = this.builder.addInstance(instance)

    override fun load() = ParticlesMesh(this.builder.load(RenderMode.TRIANGLE_STRIP))

    companion object {
        @JvmStatic
        @JvmOverloads
        fun dynamic(prototype: ParticlesMeshPrototype = Particles.meshPrototype()) = ParticlesMeshBuilder(
            InstancedArraysMeshBuilder.Dynamic(prototype,
                ParticlesMeshWriter(prototype)), prototype
        )

        @JvmStatic
        @JvmOverloads
        fun fixed(instances: Int, vertices: Int, prototype: ParticlesMeshPrototype = Particles.meshPrototype()) =
            ParticlesMeshBuilder(InstancedArraysMeshBuilder.Fixed(
                instances, vertices, prototype,
                ParticlesMeshWriter(prototype)), prototype
            )
    }
}