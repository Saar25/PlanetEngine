package org.saar.core.common.renderpass

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.rhi.opengl.shader.toOpengl
import org.saar.rhi.shader.*

class MultiplyPostProcessor(
    private val albedoBuffer: ReadOnlyTexture2D,
    private val multiply: ReadOnlyTexture,
    components: Int = 4
) : RenderPass {

    private val shadersLink = MultiplyShadersLink(components)
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()
        this.shadersLink.textureUniform.value = this.albedoBuffer
        this.shadersLink.multiplyUniform.value = this.multiply

        this.uniformsLoader.load()
        QuadMesh.draw()
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private class MultiplyShadersLink(components: Int) : ShadersLink {

        @UniformProperty
        val textureUniform = TextureUniformValue("u_texture", 0)

        @UniformProperty
        val multiplyUniform = TextureUniformValue("u_multiply", 1)

        override val shadersProgram = ShaderProgram(
            ShaderStage(
                type = ShaderStageType.VERTEX,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + Renderers.quadVertexSource)
            ),
            ShaderStage(
                type = ShaderStageType.FRAGMENT, module = ShaderModule.fromString(
                    GlslVersion.V400.toString() + "\n" +
                            "#define COMPONENTS " + components.toString() + "\n" +
                            ShaderModuleLoader.loadSource("/shaders/postprocessing/multiply.pass.glsl")
                )
            ),
        ).toOpengl()
    }
}