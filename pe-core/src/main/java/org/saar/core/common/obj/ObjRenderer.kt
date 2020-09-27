package org.saar.core.common.obj

import org.joml.Matrix4fc
import org.saar.core.renderer.AUniformProperty
import org.saar.core.renderer.AbstractRenderer
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.lwjgl.opengl.shaders.InstanceRenderState
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.StageRenderState
import org.saar.lwjgl.opengl.shaders.uniforms.UniformMat4Property
import org.saar.lwjgl.opengl.shaders.uniforms.UniformTextureProperty
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class ObjRenderer(private vararg val renderNodes: ObjRenderNode) : AbstractRenderer(shadersProgram), Renderer {

    @AUniformProperty
    private val viewProjectionUniform = object : UniformMat4Property.Stage("viewProjectionMatrix") {
        override fun getUniformValue(state: StageRenderState): Matrix4fc {
            return context!!.camera.projection.matrix.mul(context!!.camera.viewMatrix, matrix)
        }
    }

    @AUniformProperty
    private val textureUniform = object : UniformTextureProperty.Instance<ObjNode>("texture", 0) {
        override fun getUniformValue(state: InstanceRenderState<ObjNode>): ReadOnlyTexture {
            return state.instance.texture
        }
    }

    @AUniformProperty
    private val transformUniform = object : UniformMat4Property.Instance<ObjNode>("transformationMatrix") {
        override fun getUniformValue(state: InstanceRenderState<ObjNode>): Matrix4fc {
            return state.instance.transform.transformationMatrix
        }
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
        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()
        GlUtils.enableCulling()

        this.context = context

        val stageState = StageRenderState()
        viewProjectionUniform.loadOnStage(stageState)

        for (renderNode3D in this.renderNodes) {
            val state = InstanceRenderState<ObjNode>(renderNode3D)
            transformUniform.loadOnInstance(state)
            textureUniform.loadOnInstance(state)
            renderNode3D.draw()
        }
    }

    override fun onDelete() {
        for (renderNode3D in this.renderNodes) {
            renderNode3D.delete()
        }
    }
}