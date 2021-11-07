package org.saar.core.common.particles.components

import org.saar.core.common.particles.ParticlesInstance
import org.saar.core.common.particles.ParticlesModel
import org.saar.core.node.NodeComponent

class ParticlesModelComponent(val model: ParticlesModel) : NodeComponent {

    var instancesCount: Int by this.model.mesh::instancesCount

    fun readInstance(index: Int) = this.model.mesh.readInstance(index)

    fun writeInstance(index: Int, instance: ParticlesInstance) = this.model.mesh.writeInstance(index, instance)

    fun writeInstances(index: Int, instances: ParticlesInstance) = this.model.mesh.writeInstances(index, instances)

    fun writeInstances(index: Int, instances: Iterable<ParticlesInstance>) =
        this.model.mesh.writeInstances(index, instances)
}