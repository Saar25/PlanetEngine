package org.saar.core.mesh

import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.drawcall.InstancedArraysDrawCall
import org.saar.lwjgl.opengl.vao.IVao

class InstancedArraysMesh(
    private val vao: IVao,
    var drawCall: InstancedArraysDrawCall
) : Mesh {

    fun set(
        renderMode: RenderMode = this.drawCall.renderMode,
        first: Int = this.drawCall.first,
        count: Int = this.drawCall.count,
        instances: Int = this.drawCall.instances,
    ) {
        this.drawCall = InstancedArraysDrawCall(renderMode, first, count, instances)
    }

    override fun draw() {
        this.vao.bind()
        this.vao.enableAttributes()
        this.drawCall.doDrawCall()
    }

    override fun delete() {
        this.vao.delete()
    }
}