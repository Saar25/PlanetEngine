package org.saar.core.postprocessing

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.FloatUniform
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

class ContrastPostProcessor(
    private val albedoBuffer: ReadOnlyTexture2D,
    contrast: Float,
) : RenderNode {

    private val prototype = ContrastPostProcessorPrototype(contrast)
    private val wrapper = RendererPrototypeHelper(this.prototype)

    override fun render(context: RenderContext) = this.wrapper.render(context) {
        this.prototype.textureUniform.value = this.albedoBuffer
    }

    override fun delete() = this.wrapper.delete()
}

private class ContrastPostProcessorPrototype(contrast: Float) : RendererPrototype<Unit> {

    @UniformProperty
    val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    val contrastUniform = object : FloatUniform() {
        override val name = "u_contrast"

        override val value = contrast
    }

    override val shaders = arrayOf(
        Shader.createVertex(GlslVersion.V400, Renderers.vertexShaderCode),
        Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/postprocessing/contrast.pass.glsl")),
    )

    override fun doInstanceDraw(context: RenderContext, model: Unit) = QuadMesh.draw()
}