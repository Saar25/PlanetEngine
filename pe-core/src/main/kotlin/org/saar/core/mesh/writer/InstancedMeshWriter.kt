package org.saar.core.mesh.writer

import org.saar.core.mesh.Instance

interface InstancedMeshWriter<I : Instance> {
    fun writeInstance(instance: I)
}

fun <I : Instance> InstancedMeshWriter<I>.writeInstances(instances: Array<I>) =
    instances.forEach { writeInstance(it) }

fun <I : Instance> InstancedMeshWriter<I>.writeInstances(instances: Iterable<I>) =
    instances.forEach { writeInstance(it) }