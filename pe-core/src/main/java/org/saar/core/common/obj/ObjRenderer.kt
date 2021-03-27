package org.saar.core.common.obj

import org.saar.core.renderer.*
import org.saar.core.renderer.RenderState
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.renderer.uniforms.UniformUpdater
import org.saar.core.renderer.uniforms.UniformUpdaterProperty
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class ObjRenderer(private vararg val models: ObjModel) : AbstractRenderer(shadersProgram), Renderer {

    @UniformProperty
    private val viewProjectionUniform = Mat4UniformValue("viewProjectionMatrix")

    @UniformProperty
    private val textureUniform = TextureUniformValue("texture", 0)

    @UniformUpdaterProperty
    private val textureUpdater = UniformUpdater<ObjModel> { state ->
        this@ObjRenderer.textureUniform.value = state.instance.texture
    }

    @UniformProperty
    private val transformUniform = Mat4UniformValue("transformationMatrix")

    @UniformUpdaterProperty
    private val transformUpdater =
        UniformUpdater<ObjModel> { state ->
            this@ObjRenderer.transformUniform.setValue(state.instance.transform.transformationMatrix)
        }

    companion object {
        private val matrix = Matrix4.create()

        private val vertex: Shader = Shader.createVertex(
                "/shaders/obj/vertex.glsl")
        private val fragment: Shader = Shader.createFragment(
                "/shaders/obj/fragment.glsl")
        private val shadersProgram: ShadersProgram =
                ShadersProgram.create(vertex, fragment)
    }

    init {
        shadersProgram.bindAttributes("in_position", "in_uvCoord", "in_normal")
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
        for (model in this.models) {
            model.delete()
        }
    }
}