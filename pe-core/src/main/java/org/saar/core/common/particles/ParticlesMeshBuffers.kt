package org.saar.core.common.particles

import org.saar.core.mesh.buffer.DataMeshBuffer

class ParticlesMeshBuffers(
    val positionBuffer: DataMeshBuffer,
    val birthBuffer: DataMeshBuffer,
) {

    val writer = ParticlesMeshWriter(
        this.positionBuffer.writer,
        this.birthBuffer.writer,
    )

    val reader = ParticlesMeshReader(
        this.positionBuffer.reader,
        this.birthBuffer.reader,
    )

    val vertexBuffers = listOf(this.positionBuffer, this.birthBuffer).distinct()

    fun offset(index: Int) {
        this.vertexBuffers.forEach { it.offset(index) }
    }
}