package org.saar.core.common.particles

import org.saar.core.mesh.buffer.MeshInstanceBuffer
import org.saar.core.mesh.prototype.InstancedMeshPrototype
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.objects.attributes.Attributes

class ParticlesMeshPrototype(
    private val positionBuffer: MeshInstanceBuffer,
    private val birthBuffer: MeshInstanceBuffer,
) : InstancedMeshPrototype<ParticlesInstance> {

    constructor(instanceBuffer: MeshInstanceBuffer) : this(
        positionBuffer = instanceBuffer,
        birthBuffer = instanceBuffer,
    )

    init {
        this.positionBuffer.addAttribute(
            Attributes.ofInstanced(0, 3, DataType.FLOAT, false))
        this.birthBuffer.addAttribute(
            Attributes.ofIntegerInstanced(1, 1, DataType.INT))
    }

    override val instanceBuffers = arrayOf(this.positionBuffer, this.birthBuffer).distinct()

    override fun writeInstance(instance: ParticlesInstance) {
        this.positionBuffer.writer.write3f(instance.position3f)
        this.birthBuffer.writer.writeInt(instance.birth)
    }

    override fun readInstance(): ParticlesInstance {
        val position = this.positionBuffer.reader.read3f()
        val birth = this.birthBuffer.reader.readInt()
        return Particles.instance(position, birth)
    }
}