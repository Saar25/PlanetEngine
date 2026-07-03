package org.saar.core.common.renderpass

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

class AdditiveBlendPostProcessor(
    private val buffer1: ReadOnlyTexture2D,
    private val buffer2: ReadOnlyTexture2D,
) : RenderPass {

    private val shadersLink = AdditiveBlendShadersLink
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()

        this.shadersLink.texture1Uniform.value = this.buffer1
        this.shadersLink.texture2Uniform.value = this.buffer2

        this.uniformsLoader.load()

        QuadMesh.draw()
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object AdditiveBlendShadersLink : ShadersLink {

        @UniformProperty
        val texture1Uniform = TextureUniformValue("u_texture1", 0)

        @UniformProperty
        val texture2Uniform = TextureUniformValue("u_texture2", 1)

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
            Shader.createFragment(
                GlslVersion.V400,
                ShaderCode.loadSource("/shaders/postprocessing/additive-blend.pass.glsl")
            ),
        )
    }
}
