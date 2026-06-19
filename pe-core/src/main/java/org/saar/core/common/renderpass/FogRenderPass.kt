package org.saar.core.common.renderpass

import org.saar.core.fog.FogDistance
import org.saar.core.fog.FogUniformValue
import org.saar.core.fog.IFog
import org.saar.core.mesh.common.QuadMesh
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderPass
import org.saar.core.renderer.Renderers
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.ShadersUniformsLoader
import org.saar.core.renderer.state.CompositeRenderState
import org.saar.core.renderer.state.DepthTestRenderState
import org.saar.core.renderer.state.StencilTestRenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.depth.DepthState
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.IntUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec3UniformValue
import org.saar.lwjgl.opengl.stencil.StencilState
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.maths.utils.Matrix4

class FogRenderPass(
    private val albedoBuffer: ReadOnlyTexture2D,
    private val depthBuffer: ReadOnlyTexture2D,
    private val fog: IFog,
    private val fogDistance: FogDistance
) : RenderPass {

    private val shadersLink = FogShadersLink
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    override val renderState = CompositeRenderState(
        StencilTestRenderState(StencilState.REPLACE),
        DepthTestRenderState(DepthState.DISABLED),
    )

    override fun render(context: RenderContext) {
        this.shadersLink.shadersProgram.bind()
        this.shadersLink.textureUniform.value = this.albedoBuffer
        this.shadersLink.depthUniform.value = this.depthBuffer
        this.shadersLink.fogDistanceUniform.value = this.fogDistance.ordinal
        this.shadersLink.fogUniform.colourUniform.value.set(this.fog.colour)
        this.shadersLink.fogUniform.startUniform.value = this.fog.start
        this.shadersLink.fogUniform.endUniform.value = this.fog.end

        this.shadersLink.projectionMatrixInvUniform.value = context.camera
            .projection.matrix.invertPerspective(Matrix4.temp)

        this.shadersLink.cameraPositionUniform.value.set(context.camera.transform.position.value)
        this.uniformsLoader.load()
        QuadMesh.draw()
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object FogShadersLink : ShadersLink {

        @UniformProperty
        val textureUniform = TextureUniformValue("u_texture", 0)

        @UniformProperty
        val depthUniform = TextureUniformValue("u_depth", 1)

        @UniformProperty
        val fogUniform = FogUniformValue("u_fog")

        @UniformProperty
        val projectionMatrixInvUniform = Mat4UniformValue("u_projectionMatrixInv")

        @UniformProperty
        val cameraPositionUniform = Vec3UniformValue("u_cameraPosition")

        @UniformProperty
        val fogDistanceUniform = IntUniformValue("u_fogDistance")

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, Renderers.quadVertexShaderCode),
            Shader.createFragment(
                GlslVersion.V400,
                ShaderCode.define("FD_DEPTH", FogDistance.DEPTH.ordinal.toString()),
                ShaderCode.define("FD_Y", FogDistance.Y.ordinal.toString()),
                ShaderCode.define("FD_XZ", FogDistance.XZ.ordinal.toString()),
                ShaderCode.define("FD_XYZ", FogDistance.XYZ.ordinal.toString()),
                ShaderCode.loadSource("/shaders/postprocessing/fog.pass.glsl")
            ),
        )
    }
}