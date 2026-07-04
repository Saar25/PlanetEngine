package org.saar.core.common.texture3d

import org.saar.core.renderer.Renderer
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.ShadersUniformsLoader
import org.saar.core.renderer.deferred.DeferredRenderContext
import org.saar.core.renderer.init
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.FloatUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.maths.utils.Matrix4
import org.saar.rhi.opengl.resterization.toOpengl
import org.saar.rhi.resterization.CullMode
import org.saar.rhi.resterization.RasterizationState

object Texture3DDeferredRenderer : Renderer<DeferredRenderContext, Texture3DModel> {

    private val shadersLink = Texture3DDeferredRendererPrototype
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    init {
        this.shadersLink.init()
    }

    private val rasterizationState = RasterizationState(
        cullMode = CullMode.NONE,
    ).toOpengl()

    override fun render(context: DeferredRenderContext, models: Iterable<Texture3DModel>) {
        this.shadersLink.shadersProgram.bind()

        BlendTest.disable()
        DepthTest.enable()
        this.rasterizationState.set()

        this.shadersLink.normalMatrixUniform.value = context.camera.viewMatrix.invert(Matrix4.temp).transpose()

        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val vp = p.mul(v, Matrix4.create())

        models.forEach { model ->
            this.shadersLink.specularUniform.value = model.specular

            val m = model.transform.transformationMatrix

            this.shadersLink.mvpMatrixUniform.value = vp.mul(m, Matrix4.temp)

            this.shadersLink.textureUniform.value = model.texture

            this.uniformsLoader.load()

            model.draw()
        }
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object Texture3DDeferredRendererPrototype : ShadersLink {

        @UniformProperty
        val specularUniform = FloatUniformValue("u_specular")

        @UniformProperty
        val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

        @UniformProperty
        val normalMatrixUniform = Mat4UniformValue("u_normalMatrix")

        @UniformProperty
        val textureUniform = TextureUniformValue("u_texture", 0)

        override val vertexAttributes = arrayOf("in_position", "in_uvCoord")

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, ShaderCode.loadSource("/shaders/texture3d/texture3d.vertex.glsl")),
            Shader.createFragment(
                GlslVersion.V400,
                ShaderCode.loadSource("/shaders/texture3d/texture3d.dfragment.glsl")
            )
        )
    }
}