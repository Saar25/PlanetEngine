package org.saar.core.common.renderpass

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.state.DepthStencilRenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.util.Time
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.FloatUniformValue
import org.saar.rhi.depthstencil.DepthStencilState
import org.saar.rhi.depthstencil.StencilOpState

class FBMRenderPass : RenderPass {

    private val shadersLink = FBMShadersLink
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override val renderState = DepthStencilRenderState(
        DepthStencilState(stencil = StencilOpState.UNWRITTEN_ONLY)
    )

    private val startTime = Time()

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()

        this.shadersLink.timeUniform.value = this.startTime.delta().toMillis() / 1000f

        this.uniformsLoader.load()

        QuadMesh.draw()
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object FBMShadersLink : ShadersLink {

        @UniformProperty
        val timeUniform = FloatUniformValue("u_time")

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
            Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/painting/fbm.fragment.glsl")),
        )
    }
}