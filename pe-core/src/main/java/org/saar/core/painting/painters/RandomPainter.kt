package org.saar.core.painting.painters

import org.saar.core.painting.Painter
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode

class RandomPainter : Painter {

    private val prototype = RandomPainterPrototype()
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    override fun render() = this.wrapper.render()

    override fun delete() = this.wrapper.delete()
}

private class RandomPainterPrototype : RenderPassPrototype {

    override val fragmentShader: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/painting/random.fragment.glsl")
    )
}