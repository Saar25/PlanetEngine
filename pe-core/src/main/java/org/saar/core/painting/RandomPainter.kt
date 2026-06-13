package org.saar.core.painting

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeHelper
import org.saar.core.renderer.Renderers
import org.saar.core.renderer.render
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode

class RandomPainter : RenderPass {

    private val prototype = RandomPainterPrototype()
    private val wrapper = RendererPrototypeHelper(this.prototype)

    override fun render(context: RenderContext) = this.wrapper.render(context)

    override fun delete() = this.wrapper.delete()
}

private class RandomPainterPrototype : RendererPrototype<Unit> {

    override val shaders = arrayOf(
        Shader.createVertex(GlslVersion.V400, Renderers.vertexShaderCode),
        Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/painting/random.fragment.glsl")),
    )

    override fun doInstanceDraw(context: RenderContext, model: Unit) = QuadMesh.draw()
}