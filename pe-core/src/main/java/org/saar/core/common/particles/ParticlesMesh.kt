package org.saar.core.common.particles

import org.saar.core.mesh.InstancedArraysMesh
import org.saar.core.mesh.Mesh

class ParticlesMesh internal constructor(
    private val mesh: InstancedArraysMesh,
    val prototype: ParticlesMeshPrototype,
) : Mesh {
    override fun draw() = this.mesh.draw()

    override fun delete() = this.mesh.delete()

    var instancesCount: Int
        get() = this.mesh.drawCall.instances
        set(value) = this.mesh.set(instances = value)
}