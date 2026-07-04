package org.saar.core.common.flatreflected

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
import org.saar.lwjgl.opengl.shader.uniforms.FloatUniform
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec3UniformValue
import org.saar.maths.utils.Matrix4

object FlatReflectedDeferredRenderer : Renderer<DeferredRenderContext, FlatReflectedModel> {

    private val shadersLink = FlatReflectedDeferredRendererPrototype
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    init {
        this.shadersLink.init()
    }

    override fun render(context: DeferredRenderContext, models: Iterable<FlatReflectedModel>) {
        this.shadersLink.shadersProgram.bind()

        BlendTest.disable()
        DepthTest.enable()

        this.shadersLink.normalMatrixUniform.value = context.camera.viewMatrix.invert(Matrix4.temp).transpose()

        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val vp = p.mul(v, Matrix4.create())

        models.forEach { model ->
            val m = model.transform.transformationMatrix

            this.shadersLink.mvpMatrixUniform.value = vp.mul(m, Matrix4.temp)

            this.shadersLink.normalUniform.value = model.normal
            this.shadersLink.reflectionMapUniform.value = model.reflectionMap

            this.uniformsLoader.load()

            model.draw()
        }
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object FlatReflectedDeferredRendererPrototype : ShadersLink {

        @UniformProperty
        val reflectionMapUniform = TextureUniformValue("u_reflectionMap", 0)

        @UniformProperty
        val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

        @UniformProperty
        val normalUniform = Vec3UniformValue("u_normal")

        @UniformProperty
        val specularUniform = object : FloatUniform() {
            override val name = "u_specular"

            override val value = 1f
        }

        @UniformProperty
        val normalMatrixUniform = Mat4UniformValue("u_normalMatrix")

        override val vertexAttributes = arrayOf("in_position", "in_normal")

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(
                GlslVersion.V400,
                ShaderCode.loadSource("/shaders/flat-reflected/flat-reflected.vertex.glsl")
            ),
            Shader.createFragment(
                GlslVersion.V400,
                ShaderCode.loadSource("/shaders/flat-reflected/flat-reflected.dfragment.glsl")
            )
        )
    }
}