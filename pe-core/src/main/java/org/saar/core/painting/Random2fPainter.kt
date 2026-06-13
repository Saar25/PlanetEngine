package org.saar.core.painting

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode

class Random2fPainter : RenderNode {

    private val prototype = Random2fPainterPrototype()
    private val wrapper = RendererPrototypeHelper(this.prototype)

    override fun render(context: RenderContext) = this.wrapper.render(context)

    override fun delete() = this.wrapper.delete()
}

private class Random2fPainterPrototype : RendererPrototype<Unit> {

    override val shaders = arrayOf(
        Shader.createVertex(GlslVersion.V400, Renderers.vertexShaderCode),
        Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/painting/random2f.fragment.glsl")),
    )

    override fun doInstanceDraw(context: RenderContext, model: Unit) = QuadMesh.draw()
}