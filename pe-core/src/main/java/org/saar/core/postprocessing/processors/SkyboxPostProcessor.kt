package org.saar.core.postprocessing.processors

import org.saar.core.postprocessing.PostProcessingBuffers
import org.saar.core.postprocessing.PostProcessor
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniform
import org.saar.lwjgl.opengl.stencil.*
import org.saar.lwjgl.opengl.texture.CubeMapTexture
import org.saar.maths.utils.Matrix4

class SkyboxPostProcessor(cubeMap: CubeMapTexture) : PostProcessor {

    private val prototype = SkyboxPostProcessorPrototype(cubeMap)
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    override fun render(context: RenderPassContext, buffers: PostProcessingBuffers) = this.wrapper.render {
        StencilTest.apply(StencilState(StencilOperation.ALWAYS_KEEP,
            StencilFunction(Comparator.EQUAL, 0), StencilMask.UNCHANGED))

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