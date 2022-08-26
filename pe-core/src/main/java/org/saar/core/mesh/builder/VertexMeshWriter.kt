package org.saar.core.mesh.builder

import org.saar.core.mesh.Vertex

interface VertexMeshWriter<V : Vertex> {
    fun writeVertex(vertex: V)
}

fun <V : Vertex> VertexMeshWriter<V>.writeVertices(vertices: Array<V>) =
    vertices.forEach { writeVertex(it) }

fun <V : Vertex> VertexMeshWriter<V>.writeVertices(vertices: Iterable<V>) =
    vertices.forEach { writeVertex(it) }