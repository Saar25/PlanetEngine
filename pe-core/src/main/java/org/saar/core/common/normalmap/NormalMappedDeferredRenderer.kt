package org.saar.core.common.normalmap

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

class NormalMappedDeferredRenderer(private vararg val models: NormalMappedModel)
    : AbstractRenderer(), DeferredRenderer {

    @UniformProperty
    private val viewProjectionUniform = Mat4UniformValue("u_viewProjection")

    @UniformProperty
    private val transformationUniform = Mat4UniformValue("u_transformation")

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    private val normalMapUniform = TextureUniformValue("u_normalMap", 1)

    @UniformUpdaterProperty
    private val transformationUpdater = UniformUpdater<NormalMappedModel> { state ->
        this@NormalMappedDeferredRenderer.transformationUniform.setValue(state.instance.transform.transformationMatrix)
    }

    @UniformUpdaterProperty
    private val textureUpdater = UniformUpdater<NormalMappedModel> { state ->
        this@NormalMappedDeferredRenderer.textureUniform.value = state.instance.texture
    }

    @UniformUpdaterProperty
    private val normalMapUpdater = UniformUpdater<NormalMappedModel> { state ->
        this@NormalMappedDeferredRenderer.normalMapUniform.value = state.instance.normalMap
    }

    @ShaderProperty(ShaderType.VERTEX)
    private val vertex = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/normal-map/normal-map.vertex.glsl"))

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragment = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/normal-map/normal-map.dfragment.glsl"))

    companion object {
        private val matrix = Matrix4.create()
    }

    init {
        init()
        bindAttributes("in_position", "in_uvCoord",
            "in_normal", "in_tangent", "in_biTangent")
        bindFragmentOutputs("f_colour", "f_normal")
    }

    override fun preRender(context: RenderContext) {
        GlUtils.setCullFace(context.hints.cullFace)
        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()
    }

    override fun onRender(context: RenderContext) {
        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        this.viewProjectionUniform.value = p.mul(v, matrix)
        this.viewProjectionUniform.load()

        for (model in this.models) {
            val state = RenderState(model)

            this.transformationUpdater.update(state)
            this.transformationUniform.load()

            this.textureUpdater.update(state)
            this.textureUniform.load()

            this.normalMapUpdater.update(state)
            this.normalMapUniform.load()

            model.draw()
        }
    }

    override fun onDelete() {
        for (model in this.models) {
            model.delete()
        }
    }
}