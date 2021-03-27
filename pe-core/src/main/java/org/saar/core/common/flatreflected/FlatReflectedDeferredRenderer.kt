package org.saar.core.common.flatreflected

import org.saar.core.renderer.AbstractRenderer
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.RenderState
import org.saar.core.renderer.deferred.DeferredRenderer
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.*
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniform
import org.saar.lwjgl.opengl.shaders.uniforms.Vec3UniformValue
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.utils.GlCullFace
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class FlatReflectedDeferredRenderer(private vararg val models: FlatReflectedModel,
                                    private val reflectionMap: ReadOnlyTexture)
    : AbstractRenderer(), DeferredRenderer {

    @UniformProperty
    private val reflectionMapUniform = object : TextureUniform() {
        override fun getUnit(): Int = 0

        override fun getName(): String = "u_reflectionMap"

        override fun getUniformValue(): ReadOnlyTexture {
            return this@FlatReflectedDeferredRenderer.reflectionMap
        }
    }

    @UniformProperty
    private val mvpMatrixUniform = Mat4UniformValue("u_mvpMatrix")

    @UniformProperty
    private val normalUniform = Vec3UniformValue("u_normal")

    @ShaderProperty(ShaderType.VERTEX)
    private val vertex = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/flat-reflected/flat-reflected.vertex.glsl"))

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragment = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/flat-reflected/flat-reflected.dfragment.glsl"))

    companion object {
        private val matrix = Matrix4.create()
    }

    init {
        buildShadersProgram()
        bindAttributes("in_position", "in_normal")
        init()
    }

    override fun preRender(context: RenderContext) {
        GlUtils.setCullFace(GlCullFace.NONE)
        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()
        GlUtils.setProvokingVertexFirst()
    }

    override fun onRender(context: RenderContext) {
        this.reflectionMapUniform.load()

        for (model in this.models) {
            val state = RenderState(model)

            val v = context.camera.viewMatrix
            val p = context.camera.projection.matrix
            val m = state.instance.transform.transformationMatrix

            this.mvpMatrixUniform.value = p.mul(v, matrix).mul(m)
            this.mvpMatrixUniform.load()

            this.normalUniform.value = state.instance.normal
            this.normalUniform.load()

            model.draw()
        }
    }

    override fun onDelete() {
        for (model in this.models) {
            model.delete()
        }
    }
}