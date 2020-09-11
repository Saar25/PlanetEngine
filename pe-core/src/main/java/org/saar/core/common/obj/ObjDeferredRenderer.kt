package org.saar.core.common.obj

import org.joml.Matrix4fc
import org.saar.core.camera.ICamera
import org.saar.core.renderer.AbstractRenderer
import org.saar.core.renderer.annotations.InstanceUniformProperty
import org.saar.core.renderer.annotations.StageUniformProperty
import org.saar.core.renderer.deferred.DeferredRenderer
import org.saar.lwjgl.opengl.shaders.RenderState
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.UniformMat4Property
import org.saar.lwjgl.opengl.shaders.uniforms.UniformTextureProperty
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class ObjDeferredRenderer(private val camera: ICamera, private val renderNodes: Array<ObjRenderNode>)
    : AbstractRenderer(shadersProgram), DeferredRenderer {

    @StageUniformProperty
    private val viewProjectionUniform = object : UniformMat4Property<IObjNode>("viewProjectionMatrix") {
        override fun getUniformValue(state: RenderState<IObjNode>?): Matrix4fc {
            return camera.projection.matrix.mul(camera.viewMatrix, matrix)
        }
    }

    @InstanceUniformProperty
    private val textureUniform = object : UniformTextureProperty<IObjNode>("texture", 0) {
        override fun getUniformValue(state: RenderState<IObjNode>): ReadOnlyTexture {
            return state.instance.texture
        }
    }

    @InstanceUniformProperty
    private val transformUniform = object : UniformMat4Property<IObjNode>("transformationMatrix") {
        override fun getUniformValue(state: RenderState<IObjNode>): Matrix4fc {
            return state.instance.transform.transformationMatrix
        }
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
        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()
        GlUtils.enableCulling()
        init()
    }

    override fun onRender() {
        viewProjectionUniform.load(null)
        for (renderNode3D in this.renderNodes) {
            val state = RenderState<IObjNode>(renderNode3D)
            transformUniform.load(state)
            textureUniform.load(state)
            renderNode3D.render()
        }
    }

    override fun onDelete() {
        for (renderNode3D in this.renderNodes) {
            renderNode3D.delete()
        }
    }
}