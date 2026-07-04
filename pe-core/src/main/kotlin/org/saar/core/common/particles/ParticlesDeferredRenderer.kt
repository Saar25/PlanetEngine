package org.saar.core.common.particles

import org.saar.core.renderer.Renderer
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.ShadersUniformsLoader
import org.saar.core.renderer.deferred.DeferredRenderContext
import org.saar.core.renderer.init
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.depth.DepthFunction
import org.saar.lwjgl.opengl.depth.DepthMask
import org.saar.lwjgl.opengl.depth.DepthState
import org.saar.lwjgl.opengl.depth.DepthTest
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
import org.saar.rhi.opengl.resterization.toOpengl
import org.saar.rhi.resterization.CullMode
import org.saar.rhi.resterization.RasterizationState

object ParticlesDeferredRenderer : Renderer<DeferredRenderContext, ParticlesModel> {

    private val shadersLink = ParticlesDeferredRendererPrototype
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    val depthState = DepthState(DepthFunction(Comparator.LESS), DepthMask.READ)

    init {
        this.shadersLink.init()
    }

    private val rasterizationState = RasterizationState(
        cullMode = CullMode.NONE,
    ).toOpengl()

    override fun render(context: DeferredRenderContext, models: Iterable<ParticlesModel>) {
        this.shadersLink.shadersProgram.bind()

        this.rasterizationState.set()
        BlendTest.applyAlpha()
        DepthTest.apply(this.depthState)
        StencilTest.disable()

        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val vp = p.mul(v, Matrix4.create())
        this.shadersLink.viewMatrixTUniform.value = v.transpose(Matrix4.temp).m03(0f).m13(0f).m23(0f)

        models.forEach { model ->
            val m = model.transform.transformationMatrix

            this.shadersLink.mvpMatrixUniform.value = vp.mul(m, Matrix4.temp)

            this.shadersLink.textureUniform.value = model.texture
            this.shadersLink.textureAtlasSizeUniform.value = model.textureAtlasSize

            this.shadersLink.maxAgeUniform.value = model.maxAge

            this.uniformsLoader.load()

            model.draw()
        }
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object ParticlesDeferredRendererPrototype : ShadersLink {

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
            Shader.createFragment(
                GlslVersion.V400,
                ShaderCode.loadSource("/shaders/particles/particles.dfragment.glsl")
            )
        )
    }
}