package org.saar.core.common.normalmap

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShaderType
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

object NormalMappedRenderer : Renderer, RendererPrototypeWrapper<NormalMappedModel>(NormalMappedRendererPrototype())

private class NormalMappedRendererPrototype : RendererPrototype<NormalMappedModel> {

    @UniformProperty
    private val viewProjectionUniform = Mat4UniformValue("u_viewProjection")

    @UniformProperty
    private val transformationUniform = Mat4UniformValue("u_transformation")

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    private val normalMapUniform = TextureUniformValue("u_normalMap", 1)

    @ShaderProperty(ShaderType.VERTEX)
    private val vertex = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/normal-map/normal-map.vertex.glsl"))

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragment = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/normal-map/normal-map.fragment.glsl"))

    override fun vertexAttributes() = arrayOf(
        "in_position", "in_uvCoord", "in_normal", "in_tangent", "in_biTangent")

    override fun onRenderCycle(context: RenderContext) {
        GlUtils.setCullFace(context.hints.cullFace)
        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()
    }

    override fun onInstanceDraw(context: RenderContext, model: NormalMappedModel) {
        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        this.viewProjectionUniform.value = p.mul(v, Matrix4.create())

        this.transformationUniform.value = model.transform.transformationMatrix
        this.textureUniform.value = model.texture
        this.normalMapUniform.value = model.normalMap
    }

    override fun doInstanceDraw(context: RenderContext, model: NormalMappedModel) = model.draw()
}