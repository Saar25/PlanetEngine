package org.saar.core.common.flatreflected

import org.saar.core.renderer.Renderer
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.ShadersUniformsLoader
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.init
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.provokingvertex.ProvokingVertex
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec3UniformValue
import org.saar.maths.utils.Matrix4

object FlatReflectedRenderer : Renderer<ForwardRenderContext, FlatReflectedModel> {

    private val shadersLink = FlatReflectedRendererPrototype
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    init {
        this.shadersLink.init()
    }

    override fun render(context: ForwardRenderContext, models: Iterable<FlatReflectedModel>) {
        this.shadersLink.shadersProgram.bind()

        ProvokingVertex.setFirst()
        BlendTest.disable()
        DepthTest.enable()

        models.forEach { model ->
            val v = context.camera.viewMatrix
            val p = context.camera.projection.matrix
            val m = model.transform.transformationMatrix

            this.shadersLink.mvpMatrixUniform.value = p.mul(v, Matrix4.temp).mul(m)
            this.shadersLink.reflectionMapUniform.value = model.reflectionMap
            this.shadersLink.normalUniform.value = model.normal

            this.uniformsLoader.load()

            model.draw()
        }
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object FlatReflectedRendererPrototype : ShadersLink {

        @UniformProperty
        val reflectionMapUniform = TextureUniformValue("u_reflectionMap", 1)

        @UniformProperty
        val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

        @UniformProperty
        val normalUniform = Vec3UniformValue("u_normal")

        override val vertexAttributes = arrayOf("in_position", "in_normal")

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(
                GlslVersion.V400,
                ShaderCode.loadSource("/shaders/flat-reflected/flat-reflected.vertex.glsl")
            ),
            Shader.createFragment(
                GlslVersion.V400,
                ShaderCode.loadSource("/shaders/flat-reflected/flat-reflected.fragment.glsl")
            )
        )
    }
}