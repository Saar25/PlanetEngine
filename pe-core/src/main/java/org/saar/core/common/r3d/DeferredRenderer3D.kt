package org.saar.core.common.r3d

import org.saar.core.renderer.Renderer
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.ShadersUniformsLoader
import org.saar.core.renderer.deferred.DeferredRenderContext
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
import org.saar.lwjgl.opengl.shader.uniforms.FloatUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec4UniformValue
import org.saar.maths.utils.Matrix4

object DeferredRenderer3D : Renderer<DeferredRenderContext, Model3D> {

    private val shadersLink = DeferredShadersLink3D
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    init {
        this.shadersLink.init()
    }

    override fun render(context: DeferredRenderContext, models: Iterable<Model3D>) {
        this.shadersLink.shadersProgram.bind()

        ProvokingVertex.setFirst()
        BlendTest.disable()
        DepthTest.enable()
        CullFace.enable()

        this.shadersLink.normalMatrixUniform.value = context.camera.viewMatrix.invert(Matrix4.temp).transpose()

        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val vp = p.mul(v, Matrix4.create())

        models.forEach { model ->
            val m = model.transform.transformationMatrix

            this.shadersLink.specularUniform.value = model.specular
            this.shadersLink.modelMatrixUniform.value.set(m)
            this.shadersLink.mvpMatrixUniform.value = vp.mul(m, Matrix4.temp)

            this.uniformsLoader.load()

            model.mesh.draw()
        }
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    object DeferredShadersLink3D : ShadersLink {

        @UniformProperty
        val clipPlaneUniform = Vec4UniformValue("u_clipPlane")

        @UniformProperty
        val specularUniform = FloatUniformValue("u_specular")

        @UniformProperty
        val modelMatrixUniform = Mat4UniformValue("u_modelMatrix")

        @UniformProperty
        val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

        @UniformProperty
        val normalMatrixUniform = Mat4UniformValue("u_normalMatrix")

        override val vertexAttributes = arrayOf("in_position", "in_color", "in_transformation")

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, ShaderCode.loadSource("/shaders/r3d/r3d.vertex.glsl")),
            Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/r3d/r3d.dfragment.glsl"))
        )
    }
}