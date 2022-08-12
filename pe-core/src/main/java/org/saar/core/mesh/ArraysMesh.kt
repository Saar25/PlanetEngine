package org.saar.core.mesh

import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.drawcall.ArraysDrawCall
import org.saar.lwjgl.opengl.vao.IVao

class ArraysMesh(
    private val vao: IVao,
    var drawCall: ArraysDrawCall,
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
        this.drawCall.doDrawCall()
    }

    override fun delete() {
        this.vao.delete()
    }
}