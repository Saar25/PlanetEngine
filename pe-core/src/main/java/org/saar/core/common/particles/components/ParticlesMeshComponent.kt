package org.saar.core.common.particles.components

import org.saar.core.common.particles.ParticlesInstance
import org.saar.core.common.particles.ParticlesMesh
import org.saar.core.node.NodeComponent

class ParticlesMeshComponent(private val mesh: ParticlesMesh) : NodeComponent {

    fun readInstance(index: Int) = this.mesh.readInstance(index)

    fun writeInstance(index: Int, instance: ParticlesInstance) = this.mesh.writeInstance(index, instance)

    fun writeInstances(index: Int, instances: ParticlesInstance) = this.mesh.writeInstances(index, instances)

    fun writeInstances(index: Int, instances: Iterable<ParticlesInstance>) = this.mesh.writeInstances(index, instances)
}