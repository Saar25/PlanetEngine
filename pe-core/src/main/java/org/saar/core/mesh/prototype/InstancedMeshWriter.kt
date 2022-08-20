package org.saar.core.mesh.prototype

import org.saar.core.mesh.Instance

interface InstancedMeshWriter<I : Instance> {
    fun writeInstance(instance: I)
}

fun <I : Instance> InstancedMeshWriter<I>.writeVertices(instances: Array<I>) =
    instances.forEach { writeInstance(it) }

fun <I : Instance> InstancedMeshWriter<I>.writeVertices(instances: Iterable<I>) =
    instances.forEach { writeInstance(it) }