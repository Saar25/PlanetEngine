package org.saar.core.painting

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.state.StencilTestRenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.FloatUniform
import org.saar.lwjgl.opengl.stencil.StencilState

class FBMPainter : RenderPass {

    private val prototype = FBMPainterPrototype()
    private val wrapper = RendererPrototypeHelper(this.prototype)

    override val renderState = StencilTestRenderState(StencilState.UNWRITTEN_ONLY)

    override fun render(context: RenderContext) = this.wrapper.render(context)

    override fun delete() = this.wrapper.delete()
}

private class FBMPainterPrototype : RendererPrototype<Unit> {

    private val startTime = System.currentTimeMillis()

    @UniformProperty
    val timeUniform = object : FloatUniform() {
        override val name = "u_time"

        override val value get() = (System.currentTimeMillis() - startTime) / 1000f
    }

    override val shadersProgram: ShadersProgram = ShadersProgram.create(
        Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
        Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/painting/fbm.fragment.glsl")),
    )

    override fun doInstanceDraw(context: RenderContext, model: Unit) = QuadMesh.draw()
}