package org.saar.core.mesh.common

import org.saar.core.mesh.Mesh
import org.saar.lwjgl.opengl.utils.GlRendering
import org.saar.lwjgl.opengl.vao.Vao
import org.saar.rhi.inputassembly.PrimitiveTopology

object QuadMesh : Mesh {

    override fun draw() {
        Vao.EMPTY.bind()
        GlRendering.drawArrays(PrimitiveTopology.TRIANGLE_STRIP, 0, 4)
    }

    override fun delete() {
    }
}