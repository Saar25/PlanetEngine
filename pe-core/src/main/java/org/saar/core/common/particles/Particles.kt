package org.saar.core.common.particles

import org.joml.Vector3fc
import org.saar.core.mesh.buffer.MeshBufferProperty
import org.saar.core.mesh.buffer.MeshInstanceBuffer

object Particles {

    @JvmStatic
    fun instance(position: Vector3fc, age: Float): ParticlesInstance {
        return object : ParticlesInstance {
            override val position3f = position
            override val age = age
        }
    }

    @JvmStatic
    fun vertex(): ParticlesVertex {
        return object : ParticlesVertex {}
    }

    @JvmStatic
    fun meshPrototype(instanceBuffer: MeshInstanceBuffer): ParticlesMeshPrototype {
        return object : ParticlesMeshPrototype {
            @MeshBufferProperty
            val meshInstanceBuffer: MeshInstanceBuffer = instanceBuffer

            override val positionBuffer = instanceBuffer

            override val ageBuffer = instanceBuffer
        }
    }

    @JvmStatic
    fun meshPrototype(): ParticlesMeshPrototype {
        val instance = MeshInstanceBuffer.createStatic()
        return meshPrototype(instance)
    }

    @JvmStatic
    @JvmOverloads
    fun mesh(instances: Array<ParticlesInstance>, prototype: ParticlesMeshPrototype = meshPrototype()): ParticlesMesh {
        return ParticlesMeshBuilder.fixed(instances.size, 4, prototype).also {
            instances.forEach(it::addInstance)
        }.load()
    }
}