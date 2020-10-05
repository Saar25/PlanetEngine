package org.saar.core.common.obj

import org.saar.core.renderer.*
import org.saar.lwjgl.opengl.shaders.InstanceRenderState
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.StageRenderState
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class ObjRenderer(private vararg val renderNodes: ObjRenderNode) : AbstractRenderer(shadersProgram), Renderer {

    @UniformProperty
    private val viewProjectionUniform = Mat4UniformValue("viewProjectionMatrix")

    @UniformUpdater
    private val viewProjectionUpdater = StageUniformUpdater {
        val v = context!!.camera.viewMatrix
        val p = context!!.camera.projection.matrix
        this@ObjRenderer.viewProjectionUniform.value = p.mul(v, matrix)
    }

    @UniformProperty
    private val textureUniform = TextureUniformValue("texture", 0)

    @UniformUpdater
    private val textureUpdater = InstanceUniformUpdater<ObjNode> { state ->
        this@ObjRenderer.textureUniform.value = state.instance.texture
    }

    @UniformProperty
    private val transformUniform = Mat4UniformValue("transformationMatrix")

    @UniformUpdater
    private val transformUpdater = InstanceUniformUpdater<ObjNode> { state ->
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

    private var context: RenderContext? = null

    override fun onRender(context: RenderContext) {
        GlUtils.setCullFace(context.hints.cullFace)

        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()

        this.context = context

        val stageState = StageRenderState()
        viewProjectionUpdater.update(stageState)
        viewProjectionUniform.load()

        for (renderNode3D in this.renderNodes) {
            val state = InstanceRenderState<ObjNode>(renderNode3D)
            transformUpdater.update(state)
            transformUniform.load()
            textureUpdater.update(state)
            textureUniform.load()
            renderNode3D.draw()
        }
    }

    override fun onDelete() {
        for (renderNode3D in this.renderNodes) {
            renderNode3D.delete()
        }
    }
}