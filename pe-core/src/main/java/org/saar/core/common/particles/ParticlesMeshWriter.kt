package org.saar.core.common.particles

import org.saar.core.mesh.writers.InstancedArraysMeshWriter

class ParticlesMeshWriter(private val prototype: ParticlesMeshPrototype) :
    InstancedArraysMeshWriter<ParticlesVertex, ParticlesInstance> {

    override fun writeVertex(vertex: ParticlesVertex) = Unit

    override fun writeInstance(instance: ParticlesInstance) = prototype.writeInstance(instance)
}