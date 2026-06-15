package org.saar.core.postprocessing

import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.ShadersUniformsLoader
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
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.texture.CubeMapTexture
import org.saar.maths.utils.Matrix4

class SkyboxPostProcessor(private val cubeMap: CubeMapTexture) : RenderPass {

    private val shadersLink = SkyboxShadersLink
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override val renderState = CompositeRenderState(
        StencilTestRenderState(StencilState.UNWRITTEN_ONLY),
        BlendTestRenderState(BlendState(BlendFunction(BlendValue.ONE_MINUS_DST_ALPHA, BlendValue.DST_ALPHA))),
    )

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()
        this.shadersLink.projectionMatrixInvUniform.value = context.camera.projection.matrix.invert(Matrix4.temp)
        this.shadersLink.viewMatrixInvUniform.value = context.camera.viewMatrix.invert(Matrix4.temp)
        this.shadersLink.cubeMapUniform.value = this.cubeMap

        this.uniformsLoader.load()
        QuadMesh.draw()
    }

    override fun delete() {
        this.shadersLink.shadersProgram.delete()
        this.cubeMap.delete()
    }

    private object SkyboxShadersLink : ShadersLink {

        @UniformProperty
        val cubeMapUniform = TextureUniformValue("u_cubeMap", 0)

        @UniformProperty
        val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

        @UniformProperty
        val viewMatrixInvUniform = Mat4UniformValue("u_viewMatrixInv")

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, ShaderCode.loadSource("/shaders/postprocessing/skybox.vertex.glsl")),
            Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/postprocessing/skybox.pass.glsl")),
        )
    }
}