package org.saar.core.common.normalmap

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.shader.GlslVersion
import org.saar.lwjgl.opengl.shader.Shader
import org.saar.lwjgl.opengl.shader.ShaderCode
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
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

    @ShaderProperty
    private val vertex = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/normal-map/normal-map.vertex.glsl"))

    @ShaderProperty
    private val fragment = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/normal-map/normal-map.fragment.glsl"))

    override fun vertexAttributes() = arrayOf(
        "in_position", "in_uvCoord", "in_normal", "in_tangent", "in_biTangent")

    override fun onRenderCycle(context: RenderContext) {
        BlendTest.disable()
        DepthTest.enable()
    }

    override fun onInstanceDraw(context: RenderContext, model: NormalMappedModel) {
        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        this.viewProjectionUniform.value = p.mul(v, Matrix4.temp)

        this.transformationUniform.value.set(model.transform.transformationMatrix)
        this.textureUniform.value = model.texture
        this.normalMapUniform.value = model.normalMap
    }

    override fun doInstanceDraw(context: RenderContext, model: NormalMappedModel) = model.draw()
}