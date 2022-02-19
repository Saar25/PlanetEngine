package org.saar.core.mesh.prototype

import org.saar.core.mesh.Instance
import org.saar.core.mesh.buffer.MeshInstanceBuffer

interface InstancedMeshPrototype<I : Instance> {
    val instanceBuffers: List<MeshInstanceBuffer>

    fun writeInstance(instance: I)
    fun readInstance(): I
}

fun <V : Instance> InstancedMeshPrototype<V>.deleteInstances() =
    this.instanceBuffers.forEach { it.delete() }

fun <I : Instance> InstancedMeshPrototype<I>.setInstancePosition(position: Int) =
    this.instanceBuffers.forEach { it.setPosition(position) }

fun <I : Instance> InstancedMeshPrototype<I>.readInstance(position: Int): I {
    setInstancePosition(position)
    return this.readInstance()
}

fun <I : Instance> InstancedMeshPrototype<I>.writeInstance(position: Int, instance: I) =
    setInstancePosition(position).also { writeInstance(instance) }

fun <I : Instance> InstancedMeshPrototype<I>.writeInstances(position: Int, instances: Array<I>) =
    setInstancePosition(position).also { instances.forEach { writeInstance(it) } }

fun <I : Instance> InstancedMeshPrototype<I>.writeInstances(position: Int, instances: Iterable<I>) =
    setInstancePosition(position).also { instances.forEach { writeInstance(it) } }

fun <I : Instance> InstancedMeshPrototype<I>.writeInstances(instances: Array<I>) =
    instances.forEach { writeInstance(it) }

fun <I : Instance> InstancedMeshPrototype<I>.writeInstances(instances: Iterable<I>) =
    instances.forEach { writeInstance(it) }

fun <I : Instance> InstancedMeshPrototype<I>.storeInstances(from: Int, instances: Int) =
    this.instanceBuffers.forEach { it.update(from, instances) }

fun <I : Instance> InstancedMeshPrototype<I>.allocateInstances(instances: Int) =
    this.instanceBuffers.forEach { it.allocateCount(instances) }