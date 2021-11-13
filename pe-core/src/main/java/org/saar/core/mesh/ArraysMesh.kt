package org.saar.core.mesh

import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.drawcall.ArraysDrawCall
import org.saar.lwjgl.opengl.objects.vaos.IVao

class ArraysMesh(
    private val vao: IVao,
    var drawCall: ArraysDrawCall
) : Mesh {

    fun set(
        renderMode: RenderMode = this.drawCall.renderMode,
        first: Int = this.drawCall.first,
        count: Int = this.drawCall.count,
    ) {
        this.drawCall = ArraysDrawCall(renderMode, first, count)
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