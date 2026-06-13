package org.saar.core.painting

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderNode
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.state.StencilTestRenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.FloatUniform
import org.saar.lwjgl.opengl.stencil.StencilState

class FBMPainter : RenderNode {

    private val prototype = FBMPainterPrototype()
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    override val renderState = StencilTestRenderState(StencilState.UNWRITTEN_ONLY)

    override fun render(context: RenderContext) = this.wrapper.render()

    override fun delete() = this.wrapper.delete()
}

private class FBMPainterPrototype : RenderPassPrototype {

    private val startTime = System.currentTimeMillis()

    @UniformProperty
    val timeUniform = object : FloatUniform() {
        override val name = "u_time"

        override val value get() = (System.currentTimeMillis() - startTime) / 1000f
    }

    override val fragmentShader: Shader = Shader.createFragment(
        GlslVersion.V400,
        ShaderCode.loadSource("/shaders/painting/fbm.fragment.glsl")
    )
}