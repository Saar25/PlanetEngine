package org.saar.core.mesh

import org.saar.lwjgl.opengl.drawcall.DrawCall
import org.saar.lwjgl.opengl.vao.IVao

class DrawCallMesh(
    private val vao: IVao,
    private val drawCall: DrawCall,
) : Mesh {

    override fun draw() {
        this.vao.bind()
        this.drawCall.doDrawCall()
    }

    override fun delete() {
        this.vao.delete()
    }
}