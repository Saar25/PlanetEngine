package org.saar.core.common.particles

import org.saar.core.mesh.InstancedArraysMesh
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.prototype.readInstance
import org.saar.core.mesh.prototype.storeInstances
import org.saar.core.mesh.prototype.writeInstances

class ParticlesMesh internal constructor(
    private val mesh: InstancedArraysMesh,
    private val prototype: ParticlesMeshPrototype,
) : Mesh {
    override fun draw() = this.mesh.draw()

    override fun delete() = this.mesh.delete()

    var instancesCount: Int
        get() = this.mesh.drawCall.instances
        set(value) = this.mesh.set(instances = value)

    fun readInstance(index: Int): ParticlesInstance = this.prototype.readInstance(index)

    fun writeInstance(index: Int, instance: ParticlesInstance) = writeInstances(index, listOf(instance))

    fun writeInstances(index: Int, vararg instances: ParticlesInstance) = writeInstances(index, instances.asList())

    fun writeInstances(index: Int, instances: Collection<ParticlesInstance>) {
        this.prototype.writeInstances(index, instances)
        this.prototype.storeInstances(index, instances.size)
    }
}