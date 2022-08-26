package org.saar.core.common.particles.components

import org.saar.core.common.particles.ParticlesInstance
import org.saar.core.mesh.prototype.readInstance
import org.saar.core.mesh.prototype.writeInstances
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent

abstract class ParticlesControlComponent : NodeComponent {

    private lateinit var modelComponent: ParticlesModelComponent

    final override fun start(node: ComposableNode) {
        this.modelComponent = node.components.get()
    }

    final override fun update(node: ComposableNode) = Unit /*{
        val particles = (0 until this.modelComponent.instancesCount).map {
            mapInstance(it, this.modelComponent.model.mesh.prototype.readInstance(it))
        }
        val newParticles = createParticles()

        this.modelComponent.instancesCount = particles.size + newParticles.size

        this.modelComponent.model.mesh.prototype.writeInstances(0, particles + newParticles)
    }*/

    abstract fun createParticles(): Collection<ParticlesInstance>

    abstract fun mapInstance(index: Int, instance: ParticlesInstance): ParticlesInstance
}