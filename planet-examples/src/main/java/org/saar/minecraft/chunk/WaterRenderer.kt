package org.saar.minecraft.chunk

import org.joml.*
import org.saar.core.renderer.*
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.renderer.uniforms.UniformTrigger
import org.saar.lwjgl.opengl.shaders.*
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.textures.Texture
import org.saar.lwjgl.opengl.textures.Texture2D
import org.saar.lwjgl.opengl.utils.GlCullFace
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4
import org.saar.minecraft.Chunk
import org.saar.minecraft.World

private const val TRANSITION_TIME: Int = 1000

private val prototype = WaterRendererPrototype()

class WaterRenderer(private val world: World, private val atlas: Texture2D) : Renderer,
    RendererPrototypeWrapper<Chunk>(prototype) {

    override fun doRender(context: RenderContext) {
        val view = context.camera.viewMatrix
        val projection = context.camera.projection.matrix
        val frustumIntersection = FrustumIntersection(
            projection.mul(view, Matrix4.create()))

        prototype.atlas = this.atlas

        for (chunk in this.world.chunks) {
            val intersect = frustumIntersection.testAab(
                chunk.bounds.min.x().toFloat(),
                chunk.bounds.min.y().toFloat(),
                chunk.bounds.min.z().toFloat(),
                chunk.bounds.max.x().toFloat() + 1,
                chunk.bounds.max.y().toFloat() + 1,
                chunk.bounds.max.z().toFloat() + 1
            )

            if (intersect) renderModel(context, chunk)
        }
    }
}

private class WaterRendererPrototype : RendererPrototype<Chunk> {

    var atlas: ReadOnlyTexture = Texture.NULL

    @UniformProperty(UniformTrigger.PER_RENDER_CYCLE)
    private val projectionViewUniform = Mat4UniformValue("u_projectionView")

    @UniformProperty(UniformTrigger.PER_RENDER_CYCLE)
    private val atlasUniform = object : TextureUniform() {
        override fun getUnit(): Int = 0

        override fun getName(): String = "u_atlas"

        override fun getUniformValue(): ReadOnlyTexture = this@WaterRendererPrototype.atlas
    }

    @UniformProperty(UniformTrigger.PER_RENDER_CYCLE)
    private val dimensionsUniform = object : Vec2iUniform() {
        override fun getName(): String = "u_dimensions"

        override fun getUniformValue(): Vector2ic = Vector2i(16, 16)
    }

    @UniformProperty(UniformTrigger.PER_RENDER_CYCLE)
    private val texturesCount = object : IntUniform() {
        override fun getName(): String = "u_texturesCount"

        override fun getUniformValue(): Int = 4
    }

    @UniformProperty(UniformTrigger.PER_RENDER_CYCLE)
    private val transitionCross = FloatUniformValue("u_transitionCross")

    @UniformProperty(UniformTrigger.PER_RENDER_CYCLE)
    private val transitionId = IntUniformValue("u_transitionId")

    @UniformProperty
    private val chunkCoordinateUniform = Vec2iUniformValue("u_chunkCoordinate")

    @ShaderProperty(ShaderType.VERTEX)
    private val vertex: Shader = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/minecraft/shaders/water.vertex.glsl"))

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragment: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/minecraft/shaders/water.fragment.glsl"))

    override fun vertexAttributes() = arrayOf("in_data")

    override fun onRenderCycle(context: RenderContext) {
        GlUtils.enableDepthTest()
        GlUtils.setCullFace(GlCullFace.BACK)
        GlUtils.disableClipPlane(0)
        GlUtils.enableAlphaBlending()


        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        projectionViewUniform.value = p.mul(v, Matrix4.create())

        val time = System.currentTimeMillis()
        this.transitionCross.value = (time % TRANSITION_TIME) / TRANSITION_TIME.toFloat()
        this.transitionId.value = (time / TRANSITION_TIME % 4).toInt()
    }

    override fun onInstanceDraw(context: RenderContext, state: RenderState<Chunk>) {
        this.chunkCoordinateUniform.value = state.instance.position
    }
}