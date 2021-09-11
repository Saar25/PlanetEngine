package org.saar.core.painting

import org.saar.core.renderer.renderpass.RenderPassesPipelineHelper
import org.saar.lwjgl.opengl.stencil.StencilTest

class PaintingPipeline(vararg painters: Painter) {

    private val helper = RenderPassesPipelineHelper(painters)

    fun process() = this.helper.process {
        StencilTest.disable()
        it.render()
    }

    fun delete() = this.helper.delete()

}