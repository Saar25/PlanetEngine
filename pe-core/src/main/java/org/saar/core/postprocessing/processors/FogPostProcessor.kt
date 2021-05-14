package org.saar.core.postprocessing.processors

import org.joml.Matrix4f
import org.saar.core.fog.FogDistance
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
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.maths.utils.Matrix4

class FogPostProcessor(fog: IFog, applyBackground: Boolean = false, fogDistance: FogDistance) : PostProcessor,
    PostProcessorPrototypeWrapper(FogPostProcessorPrototype(fog, applyBackground, fogDistance))

private val matrix: Matrix4f = Matrix4.create()

private class FogPostProcessorPrototype(private val fog: IFog,
                                        private val applyBackground: Boolean,
                                        private val fogDistance: FogDistance) : PostProcessorPrototype {

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
    private val viewMatrixInvUniform = Mat4UniformValue("u_viewMatrixInv")

    @UniformProperty
    private val cameraPositionUniform = Vec3UniformValue("u_cameraPosition")

    @UniformProperty
    private val applyBackgroundUniform = object : BooleanUniform() {
        override fun getName() = "u_applyBackground"

        override fun getUniformValue() = this@FogPostProcessorPrototype.applyBackground
    }

    @UniformProperty
    private val fogDistanceUniform = object : IntUniform() {
        override fun getName() = "u_fogDistance"

        override fun getUniformValue() = this@FogPostProcessorPrototype.fogDistance.ordinal
    }

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("FD_Y", FogDistance.Y.ordinal.toString()),
        ShaderCode.define("FD_XZ", FogDistance.XZ.ordinal.toString()),
        ShaderCode.define("FD_XYZ", FogDistance.XYZ.ordinal.toString()),
        ShaderCode.loadSource("/shaders/postprocessing/fog.pass.glsl"))

    override fun onRender(context: PostProcessingContext) {
        this.textureUniform.value = context.buffers.colour
        this.depthUniform.value = context.buffers.depth

        context.camera!!

        this.projectionMatrixInvUniform.value = context.camera
            .projection.matrix.invertPerspective(matrix)

        this.viewMatrixInvUniform.value = context.camera
            .viewMatrix.invert(matrix)

        this.cameraPositionUniform.value = context.camera.transform.position.value
    }
}