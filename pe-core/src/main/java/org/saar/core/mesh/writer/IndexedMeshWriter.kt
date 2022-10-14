package org.saar.core.mesh.writer

interface IndexedMeshWriter {
    fun writeIndex(index: Int)
}

fun IndexedMeshWriter.writeIndices(indices: IntArray) =
    indices.forEach { writeIndex(it) }

fun IndexedMeshWriter.writeIndices(indices: Array<Int>) =
    indices.forEach { writeIndex(it) }

fun IndexedMeshWriter.writeIndices(indices: Iterable<Int>) =
    indices.forEach { writeIndex(it) }