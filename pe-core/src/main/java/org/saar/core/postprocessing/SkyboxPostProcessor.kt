package org.saar.core.postprocessing

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.*
import org.saar.core.renderer.state.BlendTestRenderState
import org.saar.core.renderer.state.CompositeRenderState
import org.saar.core.renderer.state.StencilTestRenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendFunction
import org.saar.lwjgl.opengl.blend.BlendState
import org.saar.lwjgl.opengl.blend.BlendValue
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.texture.CubeMapTexture
import org.saar.maths.utils.Matrix4

class SkyboxPostProcessor(private val cubeMap: CubeMapTexture) : RenderNode {

    private val prototype = SkyboxPostProcessorPrototype()
    private val wrapper = RendererPrototypeHelper(this.prototype)

    override val renderState = CompositeRenderState(
        StencilTestRenderState(StencilState.UNWRITTEN_ONLY),
        BlendTestRenderState(BlendState(BlendFunction(BlendValue.ONE_MINUS_DST_ALPHA, BlendValue.DST_ALPHA))),
    )

    override fun render(context: RenderContext) = this.wrapper.render(context) {
        this.prototype.projectionMatrixInvUniform.value = context.camera.projection.matrix.invert(Matrix4.temp)
        this.prototype.viewMatrixInvUniform.value = context.camera.viewMatrix.invert(Matrix4.temp)
        this.prototype.cubeMapUniform.value = this.cubeMap
    }

    override fun delete() {
        this.wrapper.delete()
        this.cubeMap.delete()
    }
}

private class SkyboxPostProcessorPrototype : RendererPrototype<Unit> {

    @UniformProperty
    val cubeMapUniform = TextureUniformValue("u_cubeMap", 0)

    @UniformProperty
    val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

    @UniformProperty
    val viewMatrixInvUniform = Mat4UniformValue("u_viewMatrixInv")

    override val shaders = arrayOf(
        Shader.createVertex(GlslVersion.V400, ShaderCode.loadSource("/shaders/postprocessing/skybox.vertex.glsl")),
        Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/postprocessing/skybox.pass.glsl")),
    )

    override fun doInstanceDraw(context: RenderContext, model: Unit) = QuadMesh.draw()
}