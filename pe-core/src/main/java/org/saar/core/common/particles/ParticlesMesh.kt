package org.saar.core.common.particles

import org.saar.core.mesh.InstancedArraysMesh
import org.saar.core.mesh.Mesh

class ParticlesMesh internal constructor(private val mesh: InstancedArraysMesh,
                                         private val prototype: ParticlesMeshPrototype) : Mesh {
    override fun draw() = this.mesh.draw()

    override fun delete() = this.mesh.delete()

    var instancesCount: Int
        get() = this.mesh.drawCall.instances
        set(value) = this.mesh.set(instances = value)

    fun readInstance(index: Int): ParticlesInstance {
        return this.prototype.readInstance(index)
    }

    fun writeInstance(index: Int, instance: ParticlesInstance) {
        this.prototype.writeInstance(index, instance)
        this.prototype.positionBuffer.update(index * 16, 16)
    }

    fun writeInstances(index: Int, vararg instances: ParticlesInstance) {
        writeInstances(index, instances.asIterable())
    }

    fun writeInstances(index: Int, instances: Iterable<ParticlesInstance>) {
        var size = 0
        instances.forEachIndexed { i, instance ->
            this.prototype.writeInstance(index + i, instance)
            size++
        }
        this.prototype.positionBuffer.update(index * 16, size * 16)
    }
}