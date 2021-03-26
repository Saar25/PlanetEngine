package org.saar.core.common.obj

import org.saar.core.renderer.*
import org.saar.core.renderer.deferred.DeferredRenderer
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class ObjDeferredRenderer(private vararg val models: ObjModel)
    : AbstractRenderer(shadersProgram), DeferredRenderer {

    @UniformProperty
    private val viewProjectionUniform = Mat4UniformValue("viewProjectionMatrix")

    @UniformProperty
    private val textureUniform = TextureUniformValue("texture", 1)

    @UniformUpdaterProperty
    private val textureUpdater = UniformUpdater<ObjModel> { state ->
        this@ObjDeferredRenderer.textureUniform.value = state.instance.texture
    }

    @UniformProperty
    private val transformUniform = Mat4UniformValue("transformationMatrix")

    @UniformUpdaterProperty
    private val transformUpdater = UniformUpdater<ObjModel> { state ->
        this@ObjDeferredRenderer.transformUniform.setValue(state.instance.transform.transformationMatrix)
    }

    companion object {
        private val matrix = Matrix4.create()

        private val vertex: Shader = Shader.createVertex(
                "/shaders/obj/vertex.glsl")
        private val fragment: Shader = Shader.createFragment(
                "/shaders/obj/fragmentDeferred.glsl")
        private val shadersProgram: ShadersProgram =
                ShadersProgram.create(vertex, fragment)
    }

    init {
        shadersProgram.bindAttributes("in_position", "in_uvCoord", "in_normal")
        shadersProgram.bindFragmentOutputs("f_colour", "f_normal")
        init()
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
            transformUpdater.update(state)
            transformUniform.load()
            textureUpdater.update(state)
            textureUniform.load()
            model.draw()
        }
    }

    override fun onDelete() {
        for (model3D in this.models) {
            model3D.delete()
        }
    }
}