package org.saar.core.common.particles

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.constants.Comparator
import org.saar.lwjgl.opengl.cullface.CullFace
import org.saar.lwjgl.opengl.depth.DepthFunction
import org.saar.lwjgl.opengl.depth.DepthMask
import org.saar.lwjgl.opengl.depth.DepthState
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.provokingvertex.ProvokingVertex
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.IntUniform
import org.saar.lwjgl.opengl.shader.uniforms.IntUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.stencil.StencilTest
import org.saar.maths.utils.Matrix4

object ParticlesDeferredRenderer : RendererPrototypeWrapper<ParticlesModel>(ParticlesDeferredRendererPrototype())

private class ParticlesDeferredRendererPrototype : RendererPrototype<ParticlesModel> {

    @UniformProperty
    private val viewMatrixTUniform = Mat4UniformValue("u_viewMatrixT")

    @UniformProperty
    private val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    private val textureAtlasSizeUniform = IntUniformValue("u_textureAtlasSize")

    @UniformProperty
    private val maxAgeUniform = IntUniformValue("u_maxAge")

    @UniformProperty
    private val currentTimeUniform = object : IntUniform() {
        override val name = "u_currentTime"

        override val value: Int get() = System.currentTimeMillis().toInt()
    }

    @ShaderProperty
    private val vertex = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/particles/particles.vertex.glsl"))

    @ShaderProperty
    private val fragment = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/particles/particles.dfragment.glsl"))

    private val depthState = DepthState(DepthFunction(Comparator.LESS), DepthMask.READ)

    override fun vertexAttributes() = arrayOf("in_position", "in_age")

    override fun onRenderCycle(context: RenderContext) {
        ProvokingVertex.setFirst();
        CullFace.disable()
        BlendTest.applyAlpha()
        DepthTest.apply(this.depthState)
        StencilTest.disable()
    }

    override fun onInstanceDraw(context: RenderContext, model: ParticlesModel) {
        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val m = model.transform.transformationMatrix

        this.mvpMatrixUniform.value = p.mul(v, Matrix4.temp).mul(m)
        this.viewMatrixTUniform.value = v.transpose(Matrix4.temp).m03(0f).m13(0f).m23(0f)

        this.textureUniform.value = model.texture
        this.textureAtlasSizeUniform.value = model.textureAtlasSize

        this.maxAgeUniform.value = model.maxAge
    }

    override fun doInstanceDraw(context: RenderContext, model: ParticlesModel) = model.draw()
}