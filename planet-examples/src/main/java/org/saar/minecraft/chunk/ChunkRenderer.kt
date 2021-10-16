package org.saar.minecraft.chunk

import org.joml.FrustumIntersection
import org.joml.Vector2i
import org.joml.Vector2ic
import org.saar.core.renderer.RenderContext
import org.saar.core.renderer.Renderer
import org.saar.core.renderer.RendererPrototype
import org.saar.core.renderer.RendererPrototypeHelper
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.blend.BlendTest
import org.saar.lwjgl.opengl.clipplane.ClipPlaneTest
import org.saar.lwjgl.opengl.depth.DepthTest
import org.saar.lwjgl.opengl.shaders.GlslVersion
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShaderCode
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture2D
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.lwjgl.opengl.utils.GlCullFace
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4
import org.saar.minecraft.Chunk
import org.saar.minecraft.World

private fun FrustumIntersection.testChunk(chunk: Chunk) = testAab(
    chunk.bounds.min.x().toFloat(),
    chunk.bounds.min.y().toFloat(),
    chunk.bounds.min.z().toFloat(),
    chunk.bounds.max.x().toFloat() + 1,
    chunk.bounds.max.y().toFloat() + 1,
    chunk.bounds.max.z().toFloat() + 1
)

object ChunkRenderer : Renderer {

    lateinit var atlas: Texture2D

    val rayCastedFace: Vec4iUniformValue
        get() = prototype.rayCastedFace

    private val prototype = ChunkRendererPrototype()
    private val helper = RendererPrototypeHelper(prototype)

    fun render(context: RenderContext, world: World) {
        val view = context.camera.viewMatrix
        val projection = context.camera.projection.matrix
        val frustumIntersection = FrustumIntersection(
            projection.mul(view, Matrix4.create())
        )

        this.prototype.atlas = this.atlas
        this.helper.render(context, world.chunks.filter { frustumIntersection.testChunk(it) })
    }

    override fun delete() {
        this.helper.delete()
        this.prototype.atlas.delete()
    }
}

private class ChunkRendererPrototype : RendererPrototype<Chunk> {

    var atlas: ReadOnlyTexture2D = Texture2D.NULL

    @UniformProperty
    val rayCastedFace = Vec4iUniformValue("u_rayCastedFace")

    @UniformProperty
    private val projectionViewUniform = Mat4UniformValue("u_projectionView")

    @UniformProperty
    private val atlasUniform = object : TextureUniform() {
        override fun getUnit(): Int = 0

        override fun getName(): String = "u_atlas"

        override fun getUniformValue(): ReadOnlyTexture = this@ChunkRendererPrototype.atlas
    }

    @UniformProperty
    private val dimensionsUniform = object : Vec2iUniform() {
        override fun getName(): String = "u_dimensions"

        override fun getUniformValue(): Vector2ic = Vector2i(16, 16)
    }

    @UniformProperty
    private val chunkCoordinateUniform = Vec2iUniformValue("u_chunkCoordinate")

    @ShaderProperty
    private val vertex: Shader = Shader.createVertex(
        GlslVersion.V400,
        ShaderCode.loadSource("/minecraft/shaders/block.vertex.glsl")
    )

    @ShaderProperty
    private val fragment: Shader = Shader.createFragment(
        GlslVersion.V400,
        ShaderCode.loadSource("/minecraft/shaders/block.fragment.glsl")
    )

    override fun vertexAttributes() = arrayOf("in_data")

    override fun onRenderCycle(context: RenderContext) {
        DepthTest.enable()
        GlUtils.setCullFace(GlCullFace.BACK)
        GlUtils.setProvokingVertexFirst()
        BlendTest.disable()
        ClipPlaneTest.disable()


        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        projectionViewUniform.value = p.mul(v, Matrix4.create())
    }

    override fun onInstanceDraw(context: RenderContext, chunk: Chunk) {
        this.chunkCoordinateUniform.value = chunk.position
    }

    override fun doInstanceDraw(context: RenderContext, instance: Chunk) = instance.drawOpaque()
}