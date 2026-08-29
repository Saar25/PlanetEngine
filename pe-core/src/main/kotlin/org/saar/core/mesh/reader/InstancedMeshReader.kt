package org.saar.core.mesh.reader

import org.saar.core.mesh.Instance

interface InstancedMeshReader<V : Instance> {
    fun readInstance(): V
}
