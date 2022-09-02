package org.saar.core.common.particles

import org.saar.core.mesh.Mesh
import org.saar.lwjgl.opengl.drawcall.InstancedArraysDrawCall
import org.saar.lwjgl.opengl.vao.IVao

class ParticlesMesh(
    private val vao: IVao,
    private val drawCall: InstancedArraysDrawCall,
    val buffers: ParticlesMeshBuffers,
) : Mesh {
    override fun draw() {
        this.vao.bind()
        this.drawCall.doDrawCall()
    }

    override fun delete() = this.vao.delete()

    var instancesCount: Int by this.drawCall::instances
}