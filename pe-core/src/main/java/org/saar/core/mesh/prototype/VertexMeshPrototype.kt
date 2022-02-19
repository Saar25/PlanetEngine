package org.saar.core.mesh.prototype

import org.saar.core.mesh.Vertex
import org.saar.core.mesh.buffer.MeshVertexBuffer

interface VertexMeshPrototype<V : Vertex> {
    val vertexBuffers: List<MeshVertexBuffer>

    fun writeVertex(vertex: V)
    fun readVertex(): V
}

fun <V : Vertex> VertexMeshPrototype<V>.deleteVertices() =
    this.vertexBuffers.forEach { it.delete() }

fun <V : Vertex> VertexMeshPrototype<V>.setVertexPosition(position: Int) =
    this.vertexBuffers.forEach { it.setPosition(position) }

fun <V : Vertex> VertexMeshPrototype<V>.readVertex(position: Int): Vertex {
    setVertexPosition(position)
    return this.readVertex()
}

fun <V : Vertex> VertexMeshPrototype<V>.writeVertex(position: Int, vertex: V) =
    setVertexPosition(position).also { writeVertex(vertex) }

fun <V : Vertex> VertexMeshPrototype<V>.writeVertices(position: Int, vertices: Array<V>) =
    setVertexPosition(position).also { vertices.forEach { writeVertex(it) } }

fun <V : Vertex> VertexMeshPrototype<V>.writeVertices(position: Int, vertices: Iterable<V>) =
    setVertexPosition(position).also { vertices.forEach { writeVertex(it) } }

fun <V : Vertex> VertexMeshPrototype<V>.writeVertices(vertices: Array<V>) =
    vertices.forEach { writeVertex(it) }

fun <V : Vertex> VertexMeshPrototype<V>.writeVertices(vertices: Iterable<V>) =
    vertices.forEach { writeVertex(it) }

fun <V : Vertex> VertexMeshPrototype<V>.storeVertices(from: Int, vertices: Int) =
    this.vertexBuffers.forEach { it.update(from, vertices) }

fun <V : Vertex> VertexMeshPrototype<V>.allocateVertices(vertices: Int) =
    this.vertexBuffers.forEach { it.allocateCount(vertices) }