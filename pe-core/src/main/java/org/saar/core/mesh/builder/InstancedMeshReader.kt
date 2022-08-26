package org.saar.core.mesh.builder

import org.saar.core.mesh.Instance

interface InstancedMeshReader<V : Instance> {
    fun readVertex(): V
}
