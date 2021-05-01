package org.saar.core.common.obj

import org.saar.core.renderer.*
import org.saar.core.renderer.deferred.DeferredRenderer
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.renderer.uniforms.UniformUpdater
import org.saar.core.renderer.uniforms.UniformUpdaterProperty
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShaderType
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class ObjDeferredRenderer(private vararg val models: ObjModel) : DeferredRenderer,
    RendererPrototypeWrapper<ObjModel>(ObjDeferredRendererPrototype()) {

    override fun render(context: RenderContext, vararg models: ObjModel) {
        super.render(context, *this.models, *models)
    }

    override fun render(context: RenderContext) {
        super.render(context, *this.models)
    }

    override fun doDelete() {
        this.models.forEach { it.delete() }
    }
}

private class ObjDeferredRendererPrototype : RendererPrototype<ObjModel> {

    @UniformProperty
    private val viewProjectionUniform = Mat4UniformValue("u_viewProjectionMatrix")

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformUpdaterProperty
    private val textureUpdater = UniformUpdater<ObjModel> { model ->
        this@ObjDeferredRendererPrototype.textureUniform.value = model.texture
    }

    @UniformProperty
    private val transformUniform = Mat4UniformValue("u_transformationMatrix")

    @UniformUpdaterProperty
    private val transformUpdater = UniformUpdater<ObjModel> { model ->
        this@ObjDeferredRendererPrototype.transformUniform.value = model.transform.transformationMatrix
    }

    @ShaderProperty(ShaderType.VERTEX)
    private val vertex = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/obj/obj.vertex.glsl"))

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragment = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/obj/obj.dfragment.glsl"))

    override fun vertexAttributes() = arrayOf(
        "in_position", "in_uvCoord", "in_normal")

    override fun fragmentOutputs() = arrayOf(
        "f_colour", "f_normal")

    override fun onRenderCycle(context: RenderContext) {
        GlUtils.setCullFace(context.hints.cullFace)
        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()
    }

    override fun onInstanceDraw(context: RenderContext, model: ObjModel) {
        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        this.viewProjectionUniform.value = p.mul(v, Matrix4.create())
    }
}