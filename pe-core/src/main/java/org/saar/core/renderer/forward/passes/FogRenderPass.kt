package org.saar.core.renderer.forward.passes

import org.saar.core.fog.FogDistance
import org.saar.core.fog.FogUniformValue
import org.saar.core.fog.IFog
import org.saar.core.renderer.forward.ForwardRenderPassPrototype
import org.saar.core.renderer.forward.ForwardRenderPassPrototypeWrapper
import org.saar.core.renderer.forward.ForwardRenderingBuffers
import org.saar.core.renderer.renderpass.RenderPassContext
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.IntUniform
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.Vec3UniformValue
import org.saar.maths.utils.Matrix4

class FogRenderPass(fog: IFog, fogDistance: FogDistance)
    : ForwardRenderPassPrototypeWrapper(FogRenderPassPrototype(fog, fogDistance))

private class FogRenderPassPrototype(private val fog: IFog, private val fogDistance: FogDistance)
    : ForwardRenderPassPrototype {

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    private val depthUniform = TextureUniformValue("u_depth", 1)

    @UniformProperty
    private val fogUniform = object : FogUniformValue() {
        override fun getName() = "u_fog"

        override fun getUniformValue() = this@FogRenderPassPrototype.fog
    }

    @UniformProperty
    private val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

    @UniformProperty
    private val viewMatrixInvUniform = Mat4UniformValue("u_viewMatrixInv")

    @UniformProperty
    private val cameraPositionUniform = Vec3UniformValue("u_cameraPosition")

    @UniformProperty
    private val fogDistanceUniform = object : IntUniform() {
        override fun getName() = "u_fogDistance"

        override fun getUniformValue() = this@FogRenderPassPrototype.fogDistance.ordinal
    }

    override fun fragmentShader(): Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("FD_DEPTH", FogDistance.DEPTH.ordinal.toString()),
        ShaderCode.define("FD_Y", FogDistance.Y.ordinal.toString()),
        ShaderCode.define("FD_XZ", FogDistance.XZ.ordinal.toString()),
        ShaderCode.define("FD_XYZ", FogDistance.XYZ.ordinal.toString()),
        ShaderCode.loadSource("/shaders/postprocessing/fog.pass.glsl"))

    override fun onRender(context: RenderPassContext, buffers: ForwardRenderingBuffers) {
        this.textureUniform.value = buffers.albedo
        this.depthUniform.value = buffers.depth

        this.projectionMatrixInvUniform.value = context.camera
            .projection.matrix.invertPerspective(Matrix4.temp)

        this.viewMatrixInvUniform.value = context.camera
            .viewMatrix.invert(Matrix4.temp)

        this.cameraPositionUniform.value = context.camera.transform.position.value
    }
}