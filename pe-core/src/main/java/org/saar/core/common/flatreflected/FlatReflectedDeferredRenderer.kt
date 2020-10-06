package org.saar.core.common.flatreflected

import org.saar.core.renderer.*
import org.saar.core.renderer.deferred.DeferredRenderer
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shaders.uniforms.TextureUniform
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.utils.GlCullFace
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class FlatReflectedDeferredRenderer(private vararg val renderNodes: FlatReflectedRenderNode,
                                    private val reflectionMap: ReadOnlyTexture)
    : AbstractRenderer(shadersProgram), DeferredRenderer {

    @UniformProperty
    private val reflectionMapUniform = object : TextureUniform() {
        override fun getUnit(): Int = 0

        override fun getName(): String = "reflectionMap"

        override fun getUniformValue(): ReadOnlyTexture {
            return this@FlatReflectedDeferredRenderer.reflectionMap
        }
    }

    @UniformProperty
    private val mvpMatrixUniform = Mat4UniformValue("mvpMatrix")

    @UniformUpdaterProperty
    private val mvpMatrixUpdater = UniformUpdater<FlatReflectedRenderNode> { state ->
        val v = context!!.camera.viewMatrix
        val p = context!!.camera.projection.matrix
        val m = state.instance.transform.transformationMatrix
        this@FlatReflectedDeferredRenderer.mvpMatrixUniform.value = p.mul(v, matrix).mul(m)
    }

    companion object {
        private val matrix = Matrix4.create()

        private val vertex: Shader = Shader.createVertex(GlslVersion.V400,
                ShaderCode.define("FLAT_SHADING", "true"),
                ShaderCode.loadSource("/shaders/flat-reflected/flat-reflected.vertex.glsl"))

        private val fragment: Shader = Shader.createFragment(GlslVersion.V400,
                ShaderCode.define("FLAT_SHADING", "true"),
                ShaderCode.loadSource("/shaders/flat-reflected/flat-reflected.dfragment.glsl"))

        private val shadersProgram: ShadersProgram = ShadersProgram.create(vertex, fragment)
    }

    init {
        shadersProgram.bindAttributes("in_position", "in_normal")
        init()
    }

    private var context: RenderContext? = null

    override fun onRender(context: RenderContext) {
        GlUtils.setCullFace(GlCullFace.NONE)

        GlUtils.enableAlphaBlending()
        GlUtils.enableDepthTest()
        GlUtils.setProvokingVertexFirst()

        this.context = context

        for (renderNode in this.renderNodes) {
            val state = RenderState(renderNode)
            mvpMatrixUpdater.update(state)
            mvpMatrixUniform.load()
            renderNode.draw()
        }
    }

    override fun onDelete() {
        for (renderNode in this.renderNodes) {
            renderNode.delete()
        }
    }
}