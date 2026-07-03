package org.saar.core.mesh.common

import org.saar.core.mesh.Mesh
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.vao.Vao
import org.saar.lwjgl.opengl.utils.GlRendering

object QuadMesh : Mesh {

    override fun draw() {
        Vao.EMPTY.bind()
        GlRendering.drawArrays(RenderMode.TRIANGLE_STRIP, 0, 4)
    }

    override fun delete() {
    }
}