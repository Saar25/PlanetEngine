package org.saar.core.mesh

import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.drawcall.ElementsDrawCall
import org.saar.lwjgl.opengl.vao.IVao

class ElementsMesh(
    private val vao: IVao,
    var drawCall: ElementsDrawCall
) : Mesh {

    fun set(
        renderMode: RenderMode = this.drawCall.renderMode,
        count: Int = this.drawCall.count,
        type: DataType = this.drawCall.type,
        indices: Long = this.drawCall.indices,
    ) {
        this.drawCall = ElementsDrawCall(renderMode, count, type, indices)
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