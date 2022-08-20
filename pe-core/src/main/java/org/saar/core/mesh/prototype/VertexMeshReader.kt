package org.saar.core.mesh.prototype

import org.saar.core.mesh.Vertex

interface VertexMeshReader<V : Vertex> {
    fun readVertex(): V
}
