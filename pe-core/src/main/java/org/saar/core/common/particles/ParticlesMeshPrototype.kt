package org.saar.core.common.particles

import org.saar.core.mesh.MeshPrototype
import org.saar.core.mesh.buffer.MeshInstanceBuffer

interface ParticlesMeshPrototype : MeshPrototype {
    val positionBuffer: MeshInstanceBuffer
    val birthBuffer: MeshInstanceBuffer
}

fun ParticlesMeshPrototype.offsetInstance(index: Int) {
    this.positionBuffer.setPosition(0)
    this.birthBuffer.setPosition(0)

    this.positionBuffer.offsetPosition(index * 12)
    this.birthBuffer.offsetPosition(index * 4)
}

fun ParticlesMeshPrototype.writeInstance(instance: ParticlesInstance) {
    this.positionBuffer.writer.write3f(instance.position3f)
    this.birthBuffer.writer.writeInt(instance.birth)
}

fun ParticlesMeshPrototype.writeInstance(index: Int, instance: ParticlesInstance) {
    offsetInstance(index)
    writeInstance(instance)
}

fun ParticlesMeshPrototype.readInstance(): ParticlesInstance {
    return this.positionBuffer.reader.let {
        Particles.instance(it.read3f(), it.readInt())
    }
}

fun ParticlesMeshPrototype.readInstance(index: Int): ParticlesInstance {
    offsetInstance(index)
    return readInstance()
}