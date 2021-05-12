package org.saar.core.postprocessing.processors

import org.joml.Matrix4f
import org.saar.core.fog.FogUniformValue
import org.saar.core.fog.IFog
import org.saar.core.postprocessing.PostProcessingContext
import org.saar.core.postprocessing.PostProcessor
import org.saar.core.postprocessing.PostProcessorPrototype
import org.saar.core.postprocessing.PostProcessorPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.BooleanUniform
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.maths.utils.Matrix4

class FogPostProcessor(fog: IFog, applyBackground: Boolean = false) : PostProcessor,
    PostProcessorPrototypeWrapper(FogPostProcessorPrototype(fog, applyBackground))

private val matrix: Matrix4f = Matrix4.create()

private class FogPostProcessorPrototype(private val fog: IFog,
                                        private val applyBackground: Boolean) : PostProcessorPrototype {

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    private val depthUniform = TextureUniformValue("u_depth", 1)

    @UniformProperty
    private val fogUniform = object : FogUniformValue() {
        override fun getName() = "u_fog"

        override fun getUniformValue() = this@FogPostProcessorPrototype.fog
    }

    @UniformProperty
    private val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

    @UniformProperty
    private val applyBackgroundUniform = object : BooleanUniform() {
        override fun getName() = "u_applyBackground"

        override fun getUniformValue() = this@FogPostProcessorPrototype.applyBackground
    }

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/postprocessing/fog.pass.glsl"))

    override fun onRender(context: PostProcessingContext) {
        this.textureUniform.value = context.buffers.colour
        this.depthUniform.value = context.buffers.depth

        this.projectionMatrixInvUniform.value = context.camera!!
            .projection.matrix.invertPerspective(matrix)
    }
}