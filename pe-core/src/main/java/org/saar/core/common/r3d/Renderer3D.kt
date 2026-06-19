package org.saar.core.common.r3d

import org.saar.core.renderer.Renderer
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.ShadersUniformsLoader
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.init
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.constants.Face
import org.saar.lwjgl.opengl.cullface.CullFace
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.provokingvertex.ProvokingVertex
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec4UniformValue
import org.saar.maths.utils.Matrix4

object Renderer3D : Renderer<ForwardRenderContext, Model3D> {

    private val shadersLink = RendererShadersLink
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    init {
        this.shadersLink.init()
    }

    override fun render(context: ForwardRenderContext, models: Iterable<Model3D>) {
        this.shadersLink.shadersProgram.bind()

        ProvokingVertex.setFirst();
        BlendTest.disable()
        DepthTest.enable()
        CullFace.set(enabled = true, face = Face.BACK)

        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val vp = p.mul(v, Matrix4.create())

        models.forEach { model ->
            val m = model.transform.transformationMatrix

            this.shadersLink.modelMatrixUniform.value.set(m)
            this.shadersLink.mvpMatrixUniform.value = vp.mul(m, Matrix4.temp)

            this.uniformsLoader.load()
            model.draw()
        }
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object RendererShadersLink : ShadersLink {

        @UniformProperty
        val clipPlaneUniform = Vec4UniformValue("u_clipPlane")

        @UniformProperty
        val modelMatrixUniform = Mat4UniformValue("u_modelMatrix")

        @UniformProperty
        val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

        override val vertexAttributes = arrayOf("in_position", "in_colour", "in_transformation")

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, ShaderCode.loadSource("/shaders/r3d/r3d.vertex.glsl")),
            Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/r3d/r3d.fragment.glsl"))
        )
    }
}