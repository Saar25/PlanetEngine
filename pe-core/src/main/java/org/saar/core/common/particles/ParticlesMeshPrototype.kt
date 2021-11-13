package org.saar.core.common.particles

import org.saar.core.mesh.MeshPrototype
import org.saar.core.mesh.buffer.MeshInstanceBuffer

interface ParticlesMeshPrototype : MeshPrototype {
    val positionBuffer: MeshInstanceBuffer
    val birthBuffer: MeshInstanceBuffer
}

val ParticlesMeshPrototype.instanceBuffers get() = arrayOf(this.positionBuffer, this.birthBuffer).distinct()

fun ParticlesMeshPrototype.offsetInstance(index: Int) = this.instanceBuffers.forEach { it.setPosition(index) }

fun ParticlesMeshPrototype.writeInstance(instance: ParticlesInstance) {
    this.positionBuffer.writer.write3f(instance.position3f)
    this.birthBuffer.writer.writeInt(instance.birth)
}

fun ParticlesMeshPrototype.readInstance(): ParticlesInstance {
    val position = this.positionBuffer.reader.read3f()
    val birth = this.birthBuffer.reader.readInt()
    return Particles.instance(position, birth)
}

fun ParticlesMeshPrototype.writeInstance(index: Int, instance: ParticlesInstance) {
    offsetInstance(index)
    writeInstance(instance)
}

fun ParticlesMeshPrototype.readInstance(index: Int): ParticlesInstance {
    offsetInstance(index)
    return readInstance()
}