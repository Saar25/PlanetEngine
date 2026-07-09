package org.saar.core.common.renderpass

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.state.DepthStencilRenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.util.Time
import org.saar.lwjgl.opengl.shader.uniforms.FloatUniformValue
import org.saar.rhi.depthstencil.DepthStencilState
import org.saar.rhi.depthstencil.StencilOpState
import org.saar.rhi.opengl.shader.toOpengl
import org.saar.rhi.shader.*

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

        override val shadersProgram = ShaderProgram(
            ShaderStage(
                type = ShaderStageType.VERTEX,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + Renderers.quadVertexSource)
            ),
            ShaderStage(
                type = ShaderStageType.FRAGMENT,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + ShaderModuleLoader.loadSource("/shaders/painting/fbm.fragment.glsl"))
            ),
        ).toOpengl()
    }
}