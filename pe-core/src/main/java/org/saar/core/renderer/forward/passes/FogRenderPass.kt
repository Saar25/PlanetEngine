package org.saar.core.renderer.forward.passes

import org.saar.core.fog.FogDistance
import org.saar.core.fog.FogUniformValue
import org.saar.core.fog.IFog
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.forward.ForwardRenderPass
import org.saar.core.renderer.forward.ForwardRenderingBuffers
import org.saar.core.renderer.renderpass.RenderPassPrototype
import org.saar.core.renderer.renderpass.RenderPassPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.IntUniform
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec3UniformValue
import org.saar.maths.utils.Matrix4

class FogRenderPass(fog: IFog, fogDistance: FogDistance) : ForwardRenderPass {

    private val prototype = FogRenderPassPrototype(fog, fogDistance)
    private val wrapper = RenderPassPrototypeWrapper(this.prototype)

    override fun render(context: RenderContext, buffers: ForwardRenderingBuffers) = this.wrapper.render {
        this.prototype.textureUniform.value = buffers.albedo
        this.prototype.depthUniform.value = buffers.depth

        this.prototype.projectionMatrixInvUniform.value = context.camera
            .projection.matrix.invertPerspective(Matrix4.temp)

        this.prototype.viewMatrixInvUniform.value = context.camera.viewMatrix.invert(Matrix4.temp)

        this.prototype.cameraPositionUniform.value.set(context.camera.transform.position.value)
    }

    override fun delete() {
        this.wrapper.delete()
    }
}

private class FogRenderPassPrototype(fog: IFog, fogDistance: FogDistance) : RenderPassPrototype {

    @UniformProperty
    val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    val depthUniform = TextureUniformValue("u_depth", 1)

    @UniformProperty
    val fogUniform = FogUniformValue("u_fog", fog)

    @UniformProperty
    val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

    @UniformProperty
    val viewMatrixInvUniform = Mat4UniformValue("u_viewMatrixInv")

    @UniformProperty
    val cameraPositionUniform = Vec3UniformValue("u_cameraPosition")

    @UniformProperty
    val fogDistanceUniform = object : IntUniform() {
        override val name = "u_fogDistance"

        override val value = fogDistance.ordinal
    }

    override val fragmentShader: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("FD_DEPTH", FogDistance.DEPTH.ordinal.toString()),
        ShaderCode.define("FD_Y", FogDistance.Y.ordinal.toString()),
        ShaderCode.define("FD_XZ", FogDistance.XZ.ordinal.toString()),
        ShaderCode.define("FD_XYZ", FogDistance.XYZ.ordinal.toString()),
        ShaderCode.loadSource("/shaders/postprocessing/fog.pass.glsl"))
}