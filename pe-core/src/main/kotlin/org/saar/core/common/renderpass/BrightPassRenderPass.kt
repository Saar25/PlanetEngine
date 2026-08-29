package org.saar.core.common.renderpass

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.uniforms.FloatUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.rhi.opengl.shader.toOpengl
import org.saar.rhi.shader.*

class BrightPassRenderPass(
    private val albedoBuffer: ReadOnlyTexture2D,
    private val threshold: Float = 0f,
) : RenderPass {

    private val shadersLink = BrightShadersLink
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()

        this.shadersLink.textureUniform.value = this.albedoBuffer
        this.shadersLink.thresholdUniform.value = this.threshold

        this.uniformsLoader.load()

        QuadMesh.draw()
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object BrightShadersLink : ShadersLink {

        @UniformProperty
        val textureUniform = TextureUniformValue("u_texture", 0)

        @UniformProperty
        val thresholdUniform = FloatUniformValue("u_threshold")

        override val shadersProgram = ShaderProgram(
            ShaderStage(
                type = ShaderStageType.VERTEX,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + Renderers.quadVertexSource)
            ),
            ShaderStage(
                type = ShaderStageType.FRAGMENT,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + ShaderModuleLoader.loadSource("/shaders/postprocessing/bright-pass.pass.glsl"))
            ),
        ).toOpengl()
    }
}
