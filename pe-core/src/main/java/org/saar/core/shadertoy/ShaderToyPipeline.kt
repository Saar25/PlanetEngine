package org.saar.core.shadertoy

import org.saar.core.renderer.renderpass.RenderPassesPipelineHelper
import org.saar.lwjgl.opengl.stencil.StencilTest

class ShaderToyPipeline(vararg toys: ShaderToy) {

    private val helper = RenderPassesPipelineHelper(toys)

    fun process() = this.helper.process {
        StencilTest.disable()
        it.render()
    }

    fun delete() = this.helper.delete()

}