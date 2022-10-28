package org.saar.core.common.particles

import org.saar.core.mesh.reader.InstancedMeshReader
import org.saar.lwjgl.util.DataReader

class ParticlesMeshReader(
    private val positionReader: DataReader,
    private val birthReader: DataReader,
) : InstancedMeshReader<ParticlesInstance> {

    override fun readInstance() = Particles.instance(
        this.positionReader.read3f(),
        this.birthReader.readInt()
    )
}