package org.saar.core.painting.painters

import org.saar.core.painting.Painter
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.FloatUniform
import org.saar.lwjgl.opengl.stencil.*

class FBMPainter : Painter {

    private val prototype = FBMPainterPrototype()
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    private val stencilState = StencilState(StencilOperation.ALWAYS_KEEP,
        StencilFunction(Comparator.EQUAL, 0), StencilMask.UNCHANGED)

    override fun prepare() = StencilTest.apply(this.stencilState)

    override fun render() = this.wrapper.render()

    override fun delete() = this.wrapper.delete()
}

private class FBMPainterPrototype : RenderPassPrototype {

    private val startTime = System.currentTimeMillis()

    @UniformProperty
    val timeUniform = object : FloatUniform() {
        override val name = "u_time"

        override val value get() = (System.currentTimeMillis() - startTime) / 1000f
    }

    override val fragmentShader: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/painting/fbm.fragment.glsl")
    )
}