package org.saar.minecraft.chunk

import org.joml.*
import org.saar.core.renderer.*
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.UniformProperty
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

private val prototype = ChunkRendererPrototype()

class ChunkRenderer(private val world: World, private val atlas: Texture2D) : Renderer,
    RendererPrototypeWrapper<Chunk>(prototype) {

    val rayCastedFace: Vec4iUniformValue
        get() = prototype.rayCastedFace

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

    override fun doRenderModel(context: RenderContext, model: Chunk) {
        model.drawOpaque()
    }
}

private class ChunkRendererPrototype : RendererPrototype<Chunk> {

    var atlas: ReadOnlyTexture = Texture.NULL

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

    @ShaderProperty(ShaderType.VERTEX)
    private val vertex: Shader = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/minecraft/shaders/block.vertex.glsl"))

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragment: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.loadSource("/minecraft/shaders/block.fragment.glsl"))

    override fun vertexAttributes() = arrayOf("in_data")

    override fun onRenderCycle(context: RenderContext) {
        GlUtils.enableDepthTest()
        GlUtils.enableDepthMasking()
        GlUtils.setCullFace(GlCullFace.BACK)
        GlUtils.setProvokingVertexFirst()
        GlUtils.disableClipPlane(0)
        GlUtils.disableBlending()


        val v = context.camera.viewMatrix
        val p = context.camera.projection.matrix
        projectionViewUniform.value = p.mul(v, Matrix4.create())
    }

    override fun onInstanceDraw(context: RenderContext, chunk: Chunk) {
        this.chunkCoordinateUniform.value = chunk.position
    }
}