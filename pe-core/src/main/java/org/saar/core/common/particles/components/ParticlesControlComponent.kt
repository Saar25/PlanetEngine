package org.saar.core.common.particles.components

import org.saar.core.common.particles.ParticlesInstance
import org.saar.core.node.ComposableNode
import org.saar.core.node.NodeComponent

abstract class ParticlesControlComponent : NodeComponent {

    private lateinit var meshComponent: ParticlesModelComponent

    final override fun start(node: ComposableNode) {
        this.meshComponent = node.components.get()
    }

    final override fun update(node: ComposableNode) {
        val particles = (0 until this.meshComponent.instancesCount).map {
            mapInstance(it, this.meshComponent.readInstance(it))
        }
        val newParticles = createParticles()

        this.meshComponent.instancesCount = particles.size + newParticles.size

        this.meshComponent.writeInstances(0, particles + newParticles)
    }

    abstract fun createParticles(): Collection<ParticlesInstance>

    abstract fun mapInstance(index: Int, instance: ParticlesInstance): ParticlesInstance
}