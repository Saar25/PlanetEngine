package org.saar.core.common.particles

import org.saar.core.mesh.builder.InstancedMeshBuilder
import org.saar.core.mesh.builder.MeshBuilder
import org.saar.lwjgl.opengl.constants.RenderMode

class ParticlesMeshBuilder private constructor(
    private val builder: InstancedMeshBuilder<ParticlesInstance>,
    private val prototype: ParticlesMeshPrototype,
) : MeshBuilder {

    fun addInstance(instance: ParticlesInstance) = this.builder.addInstance(instance)

    override fun load() = ParticlesMesh(this.builder.load(), this.prototype)

    companion object {
        @JvmStatic
        @JvmOverloads
        fun dynamic(prototype: ParticlesMeshPrototype = Particles.meshPrototype()) = ParticlesMeshBuilder(
            InstancedMeshBuilder.Dynamic(prototype, RenderMode.TRIANGLE_STRIP, 4), prototype)

        @JvmStatic
        @JvmOverloads
        fun fixed(instances: Int, prototype: ParticlesMeshPrototype = Particles.meshPrototype()) = ParticlesMeshBuilder(
            InstancedMeshBuilder.Fixed(prototype, RenderMode.TRIANGLE_STRIP, instances, 4), prototype)
    }
}