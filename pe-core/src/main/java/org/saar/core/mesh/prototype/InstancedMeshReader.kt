package org.saar.core.mesh.prototype

import org.saar.core.mesh.Instance

interface InstancedMeshReader<V : Instance> {
    fun readVertex(): V
}
