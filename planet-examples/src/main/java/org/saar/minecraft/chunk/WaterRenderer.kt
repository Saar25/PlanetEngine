package org.saar.minecraft.chunk

import org.joml.FrustumIntersection
import org.joml.Vector2i
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeHelper
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.core.renderer.uniforms.UniformTrigger
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.clipplane.ClipPlaneTest
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.lwjgl.opengl.utils.GlCullFace
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4
import org.saar.minecraft.Chunk
import org.saar.minecraft.World

private const val TRANSITION_TIME: Int = 1000

private fun FrustumIntersection.testChunk(chunk: Chunk) = testAab(
    chunk.bounds.min.x().toFloat(),
    chunk.bounds.min.y().toFloat(),
    chunk.bounds.min.z().toFloat(),
    chunk.bounds.max.x().toFloat() + 1,
    chunk.bounds.max.y().toFloat() + 1,
    chunk.bounds.max.z().toFloat() + 1
)

object WaterRenderer : Renderer {

    lateinit var atlas: ReadOnlyTexture2D

    private val prototype = WaterRendererPrototype()
    private val helper = RendererPrototypeHelper(prototype)

    fun render(context: RenderContext, world: World) {
        val view = context.camera.viewMatrix
        val projection = context.camera.projection.matrix
        val frustumIntersection = FrustumIntersection(
            projection.mul(view, Matrix4.create())
        )

        this.helper.render(context, world.chunks.filter { frustumIntersection.testChunk(it) })
    }

    override fun delete() {
        this.helper.delete()
    }
}

private class WaterRendererPrototype : RendererPrototype<Chunk> {

    @UniformProperty(UniformTrigger.PER_RENDER_CYCLE)
    private val projectionViewUniform = Mat4UniformValue("u_projectionView")

    @UniformProperty(UniformTrigger.PER_RENDER_CYCLE)
    private val atlasUniform = object : TextureUniform() {
        override val unit = 0

        override val name = "u_atlas"

        override val value get() = WaterRenderer.atlas
    }

    @UniformProperty(UniformTrigger.PER_RENDER_CYCLE)
    private val dimensionsUniform = object : Vec2iUniform() {
        override val name = "u_dimensions"

        override val value = Vector2i(16, 16)
    }

    @UniformProperty(UniformTrigger.PER_RENDER_CYCLE)
    private val texturesCount = object : IntUniform() {
        override val name = "u_texturesCount"

        override val value = 4
    }

    @UniformProperty(UniformTrigger.PER_RENDER_CYCLE)
    private val transitionCross = FloatUniformValue("u_transitionCross")

    @UniformProperty(UniformTrigger.PER_RENDER_CYCLE)
    private val transitionId = IntUniformValue("u_transitionId")

    @UniformProperty
    private val chunkCoordinateUniform = Vec2iUniformValue("u_chunkCoordinate")

    @ShaderProperty
    private val vertex: Shader = Shader.createVertex(
        GlslVersion.V400,
        ShaderCode.loadSource("/minecraft/shaders/water.vertex.glsl")
    )

    @ShaderProperty
    private val fragment: Shader = Shader.createFragment(
        GlslVersion.V400,
        ShaderCode.loadSource("/minecraft/shaders/water.fragment.glsl")
    )

    override fun vertexAttributes() = arrayOf("in_data")

    override fun onRenderCycle(context: RenderContext) {
        DepthTest.enable()
        GlUtils.setCullFace(GlCullFace.BACK)
        GlUtils.setProvokingVertexFirst()
        ClipPlaneTest.disable()
        BlendTest.applyAlpha()


        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        projectionViewUniform.value = p.mul(v, Matrix4.create())

        val time = System.currentTimeMillis()
        this.transitionCross.value = (time % TRANSITION_TIME) / TRANSITION_TIME.toFloat()
        this.transitionId.value = (time / TRANSITION_TIME % 4).toInt()
    }

    override fun onInstanceDraw(context: RenderContext, chunk: Chunk) {
        this.chunkCoordinateUniform.value = chunk.position
    }

    override fun doInstanceDraw(context: RenderContext, instance: Chunk) = instance.drawWater()
}