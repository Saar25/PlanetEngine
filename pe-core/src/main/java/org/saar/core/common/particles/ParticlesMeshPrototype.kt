package org.saar.core.common.particles

import org.saar.core.mesh.MeshPrototype
import org.saar.core.mesh.buffer.MeshInstanceBuffer

interface ParticlesMeshPrototype : MeshPrototype {
    val positionBuffer: MeshInstanceBuffer
    val birthBuffer: MeshInstanceBuffer
}