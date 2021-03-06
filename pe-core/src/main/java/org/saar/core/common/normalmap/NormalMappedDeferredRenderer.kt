package org.saar.core.common.normalmap

import org.saar.core.renderer.*
import org.saar.core.renderer.deferred.DeferredRenderer
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class NormalMappedDeferredRenderer(private vararg val models: NormalMappedModel)
    : AbstractRenderer(shadersProgram), DeferredRenderer {

    @UniformProperty
    private val viewProjectionUniform = Mat4UniformValue("u_viewProjection")

    @UniformProperty
    private val transformationUniform = Mat4UniformValue("u_transformation")

    @UniformProperty
    private val textureUniform = TextureUniformValue("u_texture", 0)

    @UniformProperty
    private val normalMapUniform = TextureUniformValue("u_normalMap", 1)

    @UniformUpdaterProperty
    private val transformationUpdater = UniformUpdater<NormalMappedNode> { state ->
        this@NormalMappedDeferredRenderer.transformationUniform.setValue(state.instance.transform.transformationMatrix)
    }

    @UniformUpdaterProperty
    private val textureUpdater = UniformUpdater<NormalMappedNode> { state ->
        this@NormalMappedDeferredRenderer.textureUniform.value = state.instance.texture
    }

    @UniformUpdaterProperty
    private val normalMapUpdater = UniformUpdater<NormalMappedNode> { state ->
        this@NormalMappedDeferredRenderer.normalMapUniform.value = state.instance.normalMap
    }

    companion object {
        private val matrix = Matrix4.create()

        private val vertex: Shader = Shader.createVertex(
                "/shaders/normal-map/normal-map.vertex.glsl")
        private val fragment: Shader = Shader.createFragment(
                "/shaders/normal-map/normal-map.dfragment.glsl")
        private val shadersProgram: ShadersProgram =
                ShadersProgram.create(vertex, fragment)
    }

    init {
        shadersProgram.bindAttributes("in_position", "in_uvCoord",
                "in_normal", "in_tangent", "in_biTangent")
        shadersProgram.bindFragmentOutputs("f_colour", "f_normal")
        init()
    }

    override fun onRender(context: RenderContext) {
        GlUtils.setCullFace(context.hints.cullFace)

        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()

        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        this.viewProjectionUniform.value = p.mul(v, matrix)
        this.viewProjectionUniform.load()

        for (model in this.models) {
            val state = RenderState<NormalMappedNode>(model)

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