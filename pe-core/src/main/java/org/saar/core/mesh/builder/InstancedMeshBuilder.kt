package org.saar.core.mesh.builder

import org.saar.core.mesh.Instance
import org.saar.core.mesh.InstancedArraysMesh
import org.saar.core.mesh.Meshes
import org.saar.core.mesh.prototype.InstancedMeshPrototype
import org.saar.core.mesh.prototype.allocateInstances
import org.saar.core.mesh.prototype.writeInstances
import org.saar.lwjgl.opengl.constants.RenderMode

abstract class InstancedMeshBuilder<I : Instance> internal constructor(
    protected val prototype: InstancedMeshPrototype<I>,
) : MeshBuilder {

    protected fun allocate(instances: Int) = this.prototype.allocateInstances(instances)

    abstract fun addInstance(instance: I)

    abstract override fun load(): InstancedArraysMesh

    class Dynamic<I : Instance>(
        prototype: InstancedMeshPrototype<I>,
        private val renderMode: RenderMode,
        private val vertices: Int,
    ) : InstancedMeshBuilder<I>(prototype) {

        private val instances = mutableListOf<I>()

        override fun addInstance(instance: I) {
            this.instances += instance
        }

        override fun load(): InstancedArraysMesh {
            allocate(this.instances.size)
            this.prototype.writeInstances(this.instances)
            return Meshes.toInstancedArrayMesh(this.prototype,
                this.vertices, this.instances.size, this.renderMode)
        }
    }

    class Fixed<I : Instance>(
        prototype: InstancedMeshPrototype<I>,
        private val renderMode: RenderMode,
        private val instances: Int,
        private val vertices: Int,
    ) : InstancedMeshBuilder<I>(prototype) {

        init {
            allocate(this.instances)
        }

        override fun addInstance(instance: I) = this.prototype.writeInstance(instance)

        override fun load() = Meshes.toInstancedArrayMesh(this.prototype,
            this.vertices, this.instances, this.renderMode)
    }
}