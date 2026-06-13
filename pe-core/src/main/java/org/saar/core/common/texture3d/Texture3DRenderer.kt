package org.saar.core.common.texture3d

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.cullface.CullFace
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.provokingvertex.ProvokingVertex
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.ShadersProgram
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.maths.utils.Matrix4

object Texture3DRenderer : Renderer, RendererPrototypeWrapper<Texture3DModel>(Texture3DRendererPrototype())

private class Texture3DRendererPrototype : RendererPrototype<Texture3DModel> {

    @UniformProperty
    private val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    override val shadersProgram: ShadersProgram = ShadersProgram.create(
        Shader.createVertex(GlslVersion.V400, ShaderCode.loadSource("/shaders/texture3d/texture3d.vertex.glsl")),
        Shader.createFragment(GlslVersion.V400, ShaderCode.loadSource("/shaders/texture3d/texture3d.fragment.glsl"))
    )

    override fun vertexAttributes() = arrayOf(
        "in_position", "in_colour", "in_transformation"
    )

    override fun onRenderCycle(context: RenderContext) {
        ProvokingVertex.setFirst();
        BlendTest.disable()
        DepthTest.enable()
        CullFace.disable()
    }

    override fun onInstanceDraw(context: RenderContext, model: Texture3DModel) {
        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        val m = model.transform.transformationMatrix

        this.mvpMatrixUniform.value = p.mul(v, Matrix4.temp).mul(m)

        this.textureUniform.value = model.texture
    }

    override fun doInstanceDraw(context: RenderContext, model: Texture3DModel) = model.draw()
}