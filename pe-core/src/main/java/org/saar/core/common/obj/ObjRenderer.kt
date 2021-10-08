package org.saar.core.common.obj

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

object ObjRenderer : Renderer, RendererPrototypeWrapper<ObjModel>(ObjRendererPrototype())

private class ObjRendererPrototype : RendererPrototype<ObjModel> {

    @UniformProperty
    private val viewProjectionUniform = Mat4UniformValue("u_viewProjectionMatrix")

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    private val transformUniform = Mat4UniformValue("u_transformationMatrix")

    @ShaderProperty
    private val vertex = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/obj/obj.vertex.glsl"))

    @ShaderProperty
    private val fragment = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/obj/obj.fragment.glsl"))

    override fun vertexAttributes() = arrayOf(
        "in_position", "in_uvCoord", "in_normal")

    override fun onRenderCycle(context: RenderContext) {
        GlUtils.setCullFace(context.hints.cullFace)
        BlendTest.disable()
        DepthTest.enable()
    }

    override fun onInstanceDraw(context: RenderContext, model: ObjModel) {
        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        this.viewProjectionUniform.value = p.mul(v, Matrix4.temp)

        this.textureUniform.value = model.texture
        this.transformUniform.value = model.transform.transformationMatrix
    }

    override fun doInstanceDraw(context: RenderContext, model: ObjModel) = model.draw()
}