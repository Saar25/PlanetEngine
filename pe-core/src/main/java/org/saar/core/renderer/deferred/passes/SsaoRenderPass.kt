package org.saar.core.renderer.deferred.passes

import org.joml.Math
import org.joml.Vector3f
import org.saar.core.painting.painters.Random2fPainter
import org.saar.core.painting.renderToTexture
import org.saar.core.renderer.deferred.DeferredRenderPass
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.glfw.window.Window
import org.saar.lwjgl.opengl.constants.ColourFormatType
import org.saar.lwjgl.opengl.constants.DataType
import org.saar.lwjgl.opengl.constants.FormatType
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.lwjgl.opengl.textures.Texture
import org.saar.lwjgl.opengl.textures.TextureTarget
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter
import org.saar.lwjgl.opengl.textures.parameters.MinFilterParameter
import org.saar.lwjgl.opengl.textures.parameters.WrapParameter
import org.saar.lwjgl.opengl.textures.settings.TextureMagFilterSetting
import org.saar.lwjgl.opengl.textures.settings.TextureMinFilterSetting
import org.saar.lwjgl.opengl.textures.settings.TextureSWrapSetting
import org.saar.lwjgl.opengl.textures.settings.TextureTWrapSetting
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Vector2
import org.saar.maths.utils.Vector3
import kotlin.random.Random

class SsaoRenderPass : DeferredRenderPass {

    private val prototype = SsaoRenderPassPrototype(createNoiseTexture(), createKernel(32))
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    private fun createNoiseTexture(): Texture {
        val painter = Random2fPainter()
        val texture = painter.renderToTexture(64, 64,
            ColourFormatType.RG16F, FormatType.RG, DataType.FLOAT)
        painter.delete()

        texture.setSettings(TextureTarget.TEXTURE_2D,
            TextureMinFilterSetting(MinFilterParameter.NEAREST),
            TextureMagFilterSetting(MagFilterParameter.NEAREST),
            TextureSWrapSetting(WrapParameter.REPEAT),
            TextureTWrapSetting(WrapParameter.REPEAT))

        return texture
    }

    private fun createKernel(size: Int) = Array(size) { i ->
        val x = Random.nextFloat() * 2 - 1
        val y = Random.nextFloat() * 2 - 1
        val z = Random.nextFloat()

        val scale = Math.lerp(.1f, 1f, (i / 64f) * (i / 64f))

        Vector3.of(x, y, z).normalize(Random.nextFloat() * scale)
    }

    override fun render(context: RenderPassContext, buffers: DeferredRenderingBuffers) = this.wrapper.render {
        this.prototype.colourTextureUniform.value = buffers.albedo
        this.prototype.normalTextureUniform.value = buffers.normal
        this.prototype.depthTextureUniform.value = buffers.depth

        this.prototype.projectionMatrixInvUniform.value =
            context.camera.projection.matrix.invertPerspective(Matrix4.temp)

        this.prototype.projectionMatrixUniform.value = context.camera.projection.matrix
    }

    override fun delete() {
        this.wrapper.delete()
        this.prototype.noiseTexture.delete()
    }
}

private class SsaoRenderPassPrototype(val noiseTexture: Texture, val kernel: Array<Vector3f>) : RenderPassPrototype {

    @UniformProperty
    val colourTextureUniform = TextureUniformValue("u_colourTexture", 0)

    @UniformProperty
    val normalTextureUniform = TextureUniformValue("u_normalTexture", 1)

    @UniformProperty
    val depthTextureUniform = TextureUniformValue("u_depthTexture", 2)

    @UniformProperty
    private val noiseTextureUniform = object : TextureUniform() {
        override fun getUniformValue() = noiseTexture

        override fun getName() = "u_noiseTexture"

        override fun getUnit() = 3
    }

    @UniformProperty
    private val kernelUniform = UniformArray("u_kernel", this.kernel.size) { name, index ->
        object : Vec3Uniform() {
            override fun getUniformValue() = kernel[index]

            override fun getName() = name
        }
    }

    @UniformProperty
    val noiseScaleUniform = object : Vec2Uniform() {
        override fun getName() = "u_noiseScale"

        override fun getUniformValue() = Vector2.of(
            Window.current().width.toFloat(),
            Window.current().height.toFloat()
        ).div(kernel.size.toFloat())
    }

    @UniformProperty
    val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

    @UniformProperty
    val projectionMatrixUniform = Mat4UniformValue("u_projectionMatrix")

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("KERNEL_SAMPLES", kernel.size.toString()),
        ShaderCode.loadSource("/shaders/deferred/ssao/ssao.fragment.glsl"))
}