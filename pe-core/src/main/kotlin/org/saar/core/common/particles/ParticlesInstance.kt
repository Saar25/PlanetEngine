package org.saar.core.common.particles

import org.joml.Vector3fc
import org.saar.core.mesh.Instance

interface ParticlesInstance : Instance {
    val position3f: Vector3fc
    val birth: Int
}