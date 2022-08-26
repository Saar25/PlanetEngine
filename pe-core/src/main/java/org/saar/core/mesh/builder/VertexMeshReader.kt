package org.saar.core.mesh.builder

import org.saar.core.mesh.Vertex

interface VertexMeshReader<V : Vertex> {
    fun readVertex(): V
}
