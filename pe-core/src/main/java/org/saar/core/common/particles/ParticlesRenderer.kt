package org.saar.core.common.particles

import org.saar.core.renderer.Renderer
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.ShadersUniformsLoader
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.init
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.cullface.CullFace
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.provokingvertex.ProvokingVertex
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.IntUniform
import org.saar.lwjgl.opengl.shader.uniforms.IntUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.stencil.StencilTest
import org.saar.maths.utils.Matrix4

object ParticlesRenderer : Renderer<ForwardRenderContext, ParticlesModel> {

    private val shadersLink = ParticlesRendererPrototype
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    init {
        this.shadersLink.init()
    }

    override fun render(context: ForwardRenderContext, models: Iterable<ParticlesModel>) {
        this.shadersLink.shadersProgram.bind()

        ProvokingVertex.setFirst();
        CullFace.disable()
        BlendTest.applyAlpha()
        DepthTest.enable()
        StencilTest.enable()

        models.forEach { model ->
            val v = context.camera.viewMatrix
            val p = context.camera.projection.matrix
            val m = model.transform.transformationMatrix

            this.shadersLink.mvpMatrixUniform.value = p.mul(v, Matrix4.temp).mul(m)
            this.shadersLink.viewMatrixTUniform.value = v.transpose(Matrix4.temp).m03(0f).m13(0f).m23(0f)

            this.shadersLink.textureUniform.value = model.texture
            this.shadersLink.textureAtlasSizeUniform.value = model.textureAtlasSize

            this.shadersLink.maxAgeUniform.value = model.maxAge

            this.uniformsLoader.load()

            model.draw()
        }
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object ParticlesRendererPrototype : ShadersLink {

        @UniformProperty
        val viewMatrixTUniform = Mat4UniformValue("u_viewMatrixT")

        @UniformProperty
        val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

        @UniformProperty
        val textureUniform = TextureUniformValue("u_texture", 0)

        @UniformProperty
        val textureAtlasSizeUniform = IntUniformValue("u_textureAtlasSize")

        @UniformProperty
        val maxAgeUniform = IntUniformValue("u_maxAge")

        @UniformProperty
        val currentTimeUniform = object : IntUniform() {
            override val name = "u_currentTime"

            override val value: Int get() = System.currentTimeMillis().toInt()
        }

        override val vertexAttributes = arrayOf("in_position", "in_age")

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, ShaderCode.loadSource("/shaders/particles/particles.vertex.glsl")),
            Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/particles/particles.fragment.glsl"))
        )
    }
}