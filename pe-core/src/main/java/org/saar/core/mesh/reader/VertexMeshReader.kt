package org.saar.core.mesh.reader

import org.saar.core.mesh.Vertex

interface VertexMeshReader<V : Vertex> {
    fun readVertex(): V
}
