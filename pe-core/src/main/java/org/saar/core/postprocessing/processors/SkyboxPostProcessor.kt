package org.saar.core.postprocessing.processors

import org.saar.core.postprocessing.PostProcessingBuffers
import org.saar.core.postprocessing.PostProcessorPrototype
import org.saar.core.postprocessing.PostProcessorPrototypeWrapper
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.stencil.*
import org.saar.lwjgl.opengl.textures.CubeMapTexture
import org.saar.maths.utils.Matrix4

class SkyboxPostProcessor(cubeMap: CubeMapTexture)
    : PostProcessorPrototypeWrapper(SkyboxPostProcessorPrototype(cubeMap))

private class SkyboxPostProcessorPrototype(private val cubeMap: CubeMapTexture) : PostProcessorPrototype {

    @UniformProperty
    private val cubeMapUniform = TextureUniformValue("u_cubeMap", 0)

    @UniformProperty
    private val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

    @UniformProperty
    private val viewMatrixInvUniform = Mat4UniformValue("u_viewMatrixInv")

    override fun vertexShader(): Shader = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/postprocessing/skybox.vertex.glsl"))

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/postprocessing/skybox.pass.glsl"))

    override fun onRender(context: RenderPassContext, buffers: PostProcessingBuffers) {
        StencilTest.apply(StencilState(StencilOperation.ALWAYS_KEEP,
            StencilFunction(Comparator.EQUAL, 0), StencilMask.UNCHANGED))

        this.cubeMapUniform.value = this.cubeMap

        this.projectionMatrixInvUniform.value = context.camera.projection.matrix.invert(Matrix4.temp)
        this.viewMatrixInvUniform.value = context.camera.viewMatrix.invert(Matrix4.temp)
    }
}