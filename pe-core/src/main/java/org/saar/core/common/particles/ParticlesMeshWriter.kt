package org.saar.core.common.particles

import org.saar.core.mesh.writer.InstancedMeshWriter
import org.saar.lwjgl.util.DataWriter

class ParticlesMeshWriter(
    private val positionWriter: DataWriter,
    private val birthWriter: DataWriter,
) : InstancedMeshWriter<ParticlesInstance> {

    override fun writeInstance(instance: ParticlesInstance) {
        this.positionWriter.write3f(instance.position3f)
        this.birthWriter.writeInt(instance.birth)
    }
}