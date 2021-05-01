package org.saar.core.common.normalmap

import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeWrapper
import org.saar.core.renderer.deferred.DeferredRenderer
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.renderer.uniforms.UniformTrigger
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShaderType
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class NormalMappedDeferredRenderer(private vararg val models: NormalMappedModel) : DeferredRenderer,
    RendererPrototypeWrapper<NormalMappedModel>(NormalMappedPrototype()) {

    override fun render(context: RenderContext, vararg models: NormalMappedModel) {
        super.render(context, *this.models, *models)
    }

    override fun render(context: RenderContext) {
        super.render(context, *this.models)
    }

    override fun doDelete() {
        this.models.forEach { it.delete() }
    }
}

private class NormalMappedPrototype : RendererPrototype<NormalMappedModel> {

    @UniformProperty(UniformTrigger.PER_RENDER_CYCLE)
    private val viewProjectionUniform = Mat4UniformValue("u_viewProjection")

    @UniformProperty(UniformTrigger.PER_INSTANCE)
    private val transformationUniform = Mat4UniformValue("u_transformation")

    @UniformProperty(UniformTrigger.PER_INSTANCE)
    private val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty(UniformTrigger.PER_INSTANCE)
    private val normalMapUniform = TextureUniformValue("u_normalMap", 1)

    @ShaderProperty(ShaderType.VERTEX)
    private val vertex = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/normal-map/normal-map.vertex.glsl"))

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragment = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/normal-map/normal-map.dfragment.glsl"))

    override fun vertexAttributes() = arrayOf(
        "in_position", "in_uvCoord", "in_normal", "in_tangent", "in_biTangent")

    override fun fragmentOutputs() = arrayOf(
        "f_colour", "f_normal")

    override fun onRenderCycle(context: RenderContext) {
        GlUtils.setCullFace(context.hints.cullFace)
        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()

        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        this.viewProjectionUniform.value = p.mul(v, Matrix4.create())
    }

    override fun onInstanceDraw(context: RenderContext, model: NormalMappedModel) {
        this.transformationUniform.value = model.transform.transformationMatrix
        this.textureUniform.value = model.texture
        this.normalMapUniform.value = model.normalMap
    }
}