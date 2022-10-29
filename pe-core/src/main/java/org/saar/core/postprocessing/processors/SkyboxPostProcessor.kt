package org.saar.core.postprocessing.processors

import org.saar.core.postprocessing.PostProcessingBuffers
import org.saar.core.postprocessing.PostProcessor
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendFunction
import org.saar.lwjgl.opengl.blend.BlendState
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.blend.BlendValue
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniform
import org.saar.lwjgl.opengl.stencil.StencilTest
import org.saar.lwjgl.opengl.texture.CubeMapTexture
import org.saar.maths.utils.Matrix4

class SkyboxPostProcessor(cubeMap: CubeMapTexture) : PostProcessor {

    private val prototype = SkyboxPostProcessorPrototype(cubeMap)
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    private val blendState = BlendState(
        BlendFunction(BlendValue.ONE_MINUS_DST_ALPHA, BlendValue.DST_ALPHA)
    )

    override fun prepare(context: RenderContext, buffers: PostProcessingBuffers) {
        StencilTest.disable()
        BlendTest.apply(this.blendState)
    }

    override fun render(context: RenderContext, buffers: PostProcessingBuffers) = this.wrapper.render {
        this.prototype.projectionMatrixInvUniform.value = context.camera.projection.matrix.invert(Matrix4.temp)
        this.prototype.viewMatrixInvUniform.value = context.camera.viewMatrix.invert(Matrix4.temp)
    }

    override fun delete() {
        this.wrapper.delete()
        this.prototype.cubeMap.delete()
    }
}

private class SkyboxPostProcessorPrototype(val cubeMap: CubeMapTexture) : RenderPassPrototype {

    @UniformProperty
    val cubeMapUniform = object : TextureUniform() {
        override val unit = 0

        override val name = "u_cubeMap"

        override val value get() = cubeMap
    }

    @UniformProperty
    val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

    @UniformProperty
    val viewMatrixInvUniform = Mat4UniformValue("u_viewMatrixInv")

    override val vertexShader: Shader = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/postprocessing/skybox.vertex.glsl"))

    override val fragmentShader: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/postprocessing/skybox.pass.glsl"))
}