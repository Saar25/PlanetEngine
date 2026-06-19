package org.saar.core.common.obj

import org.saar.core.renderer.Renderer
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.ShadersUniformsLoader
import org.saar.core.renderer.forward.ForwardRenderContext
import org.saar.core.renderer.init
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.maths.utils.Matrix4

object ObjRenderer : Renderer<ForwardRenderContext, ObjModel> {

    private val shadersLink = ObjRendererPrototype
    private val uniformsLoader = ShadersUniformsLoader.from(this.shadersLink)

    init {
        this.shadersLink.init()
    }

    override fun render(context: ForwardRenderContext, models: Iterable<ObjModel>) {
        this.shadersLink.shadersProgram.bind()

        BlendTest.disable()
        DepthTest.enable()

        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        this.shadersLink.viewProjectionUniform.value = p.mul(v, Matrix4.temp)

        models.forEach { model ->
            this.shadersLink.textureUniform.value = model.texture
            this.shadersLink.transformUniform.value.set(model.transform.transformationMatrix)

            this.uniformsLoader.load()

            model.draw()
        }
    }

    override fun delete() = this.shadersLink.shadersProgram.delete()

    private object ObjRendererPrototype : ShadersLink {

        @UniformProperty
        val viewProjectionUniform = Mat4UniformValue("u_viewProjectionMatrix")

        @UniformProperty
        val textureUniform = TextureUniformValue("u_texture", 0)

        @UniformProperty
        val transformUniform = Mat4UniformValue("u_transformationMatrix")

        override val vertexAttributes = arrayOf("in_position", "in_uvCoord", "in_normal")

        override val shadersProgram: ShadersProgram = ShadersProgram.create(
            Shader.createVertex(GlslVersion.V400, ShaderCode.loadSource("/shaders/obj/obj.vertex.glsl")),
            Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/obj/obj.fragment.glsl"))
        )
    }
}