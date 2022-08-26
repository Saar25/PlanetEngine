package org.saar.core.common.particles

import org.joml.Vector3fc
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.builder.MeshBufferBuilder
import org.saar.core.mesh.prototype.writeInstances
import org.saar.lwjgl.opengl.vbo.VboUsage
import org.saar.lwjgl.util.buffer.FixedBufferBuilder

object Particles {

    @JvmStatic
    @JvmOverloads
    fun instance(position: Vector3fc, birth: Int = System.currentTimeMillis().toInt()): ParticlesInstance {
        return object : ParticlesInstance {
            override val position3f = position
            override val birth = birth
        }
    }

    @JvmStatic
    fun mesh(instances: Array<ParticlesInstance>): Mesh {
        val instanceBufferBuilder = MeshBufferBuilder(
            FixedBufferBuilder(instances.size * 4 * 4),
            VboUsage.STATIC_DRAW)

        val objMeshBuilder = ParticlesMeshBuilder(instances.size,
            instanceBufferBuilder, instanceBufferBuilder)

        objMeshBuilder.writer.writeInstances(instances)

        return objMeshBuilder.load()
    }
}