package org.saar.core.mesh

import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.drawcall.InstancedElementsDrawCall
import org.saar.lwjgl.opengl.vao.IVao

class InstancedElementsMesh(
    private val vao: IVao,
    var drawCall: InstancedElementsDrawCall
) : Mesh {

    fun set(
        renderMode: RenderMode = this.drawCall.renderMode,
        count: Int = this.drawCall.count,
        type: DataType = this.drawCall.type,
        indices: Long = this.drawCall.indices,
        instances: Int = this.drawCall.instances,
    ) {
        this.drawCall = InstancedElementsDrawCall(renderMode, count, type, indices, instances)
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