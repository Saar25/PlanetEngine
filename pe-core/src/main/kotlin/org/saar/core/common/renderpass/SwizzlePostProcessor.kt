package org.saar.core.common.renderpass

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.state.DepthStencilRenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.rhi.depthstencil.DepthStencilState
import org.saar.rhi.opengl.shader.toOpengl
import org.saar.rhi.shader.*

class SwizzlePostProcessor(
    private val albedoBuffer: ReadOnlyTexture2D,
    r: Swizzle, g: Swizzle, b: Swizzle, a: Swizzle
) : RenderPass {

    private val shadersLink = SwizzleShadersLink(r, g, b, a)
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override val renderState = DepthStencilRenderState(DepthStencilState())

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()
        this.shadersLink.textureUniform.value = this.albedoBuffer

        this.uniformsLoader.load()
        QuadMesh.draw()
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private class SwizzleShadersLink(r: Swizzle, g: Swizzle, b: Swizzle, a: Swizzle) : ShadersLink {

        @UniformProperty
        val textureUniform = TextureUniformValue("u_texture", 0)

        override val shadersProgram = ShaderProgram(
            ShaderStage(
                type = ShaderStageType.VERTEX,
                module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + Renderers.quadVertexSource)
            ),
            ShaderStage(
                type = ShaderStageType.FRAGMENT, module = ShaderModule.fromString(
                    GlslVersion.V400.toString() + "\n" +
                            "#define R " + r.name.lowercase() + "\n" +
                            "#define G " + g.name.lowercase() + "\n" +
                            "#define B " + b.name.lowercase() + "\n" +
                            "#define A " + a.name.lowercase() + "\n" +
                            ShaderModuleLoader.loadSource("/shaders/postprocessing/swizzle.pass.glsl")
                )
            ),
        ).toOpengl()
    }
}

enum class Swizzle {
    R, G, B, A
}