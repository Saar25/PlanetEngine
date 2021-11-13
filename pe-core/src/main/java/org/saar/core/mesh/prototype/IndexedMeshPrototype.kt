package org.saar.core.mesh.prototype

import org.saar.core.mesh.buffer.MeshIndexBuffer

interface IndexedMeshPrototype {
    val indexBuffers: List<MeshIndexBuffer>

    fun writeIndex(index: Int)
    fun readIndex(): Int
}

fun IndexedMeshPrototype.setIndexPosition(position: Int) =
    this.indexBuffers.forEach { it.setPosition(position) }

fun IndexedMeshPrototype.readIndex(position: Int): Int {
    setIndexPosition(position)
    return this.readIndex()
}

fun IndexedMeshPrototype.writeIndex(position: Int, index: Int) =
    setIndexPosition(position).also { writeIndex(index) }

fun IndexedMeshPrototype.writeIndices(position: Int, indices: IntArray) =
    setIndexPosition(position).also { indices.forEach { writeIndex(it) } }

fun IndexedMeshPrototype.writeIndices(position: Int, indices: Iterable<Int>) =
    setIndexPosition(position).also { indices.forEach { writeIndex(it) } }

fun IndexedMeshPrototype.writeIndices(indices: IntArray) =
    indices.forEach { writeIndex(it) }

fun IndexedMeshPrototype.writeIndices(indices: Iterable<Int>) =
    indices.forEach { writeIndex(it) }

fun IndexedMeshPrototype.storeIndices(from: Int, indices: Int) =
    this.indexBuffers.forEach { it.update(from, indices) }

fun IndexedMeshPrototype.allocateIndices(indices: Int) =
    this.indexBuffers.forEach { it.allocateCount(indices) }