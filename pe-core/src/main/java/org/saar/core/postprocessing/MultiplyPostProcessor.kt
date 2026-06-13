package org.saar.core.postprocessing

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D

class MultiplyPostProcessor(
    private val albedoBuffer: ReadOnlyTexture2D,
    private val multiply: ReadOnlyTexture,
    components: Int = 4
) : RenderNode {

    private val prototype = MultiplyPostProcessorPrototype(components)
    private val wrapper = RendererPrototypeHelper(this.prototype)

    override fun render(context: RenderContext) = this.wrapper.render(context) {
        this.prototype.textureUniform.value = this.albedoBuffer
        this.prototype.multiplyUniform.value = this.multiply
    }

    override fun delete() = this.wrapper.delete()
}

private class MultiplyPostProcessorPrototype(components: Int) : RendererPrototype<Unit> {

    @UniformProperty
    val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    val multiplyUniform = TextureUniformValue("u_multiply", 1)

    override val shaders = arrayOf(
        Shader.createVertex(GlslVersion.V400, Renderers.vertexShaderCode),
        Shader.createFragment(
            GlslVersion.V400,
            ShaderCode.define("COMPONENTS", components.toString()),
            ShaderCode.loadSource("/shaders/postprocessing/multiply.pass.glsl")
        ),
    )

    override fun doInstanceDraw(context: RenderContext, model: Unit) = QuadMesh.draw()
}