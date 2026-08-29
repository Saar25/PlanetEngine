package org.saar.minecraft.chunk

import org.joml.FrustumIntersection
import org.joml.Vector2i
import org.saar.core.camera.ICamera
import org.saar.core.renderer.ShadersLink
import org.saar.core.renderer.ShadersUniformsLoader
import org.saar.core.renderer.init
import org.saar.core.renderer.uniforms.UniformProperty
import org.saar.lwjgl.opengl.clipplane.ClipPlaneTest
import org.saar.lwjgl.opengl.shader.uniforms.FloatUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Mat4UniformValue
import org.saar.lwjgl.opengl.shader.uniforms.TextureUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec2iUniformValue
import org.saar.lwjgl.opengl.shader.uniforms.Vec4iUniformValue
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.maths.utils.Matrix4
import org.saar.minecraft.Chunk
import org.saar.minecraft.World
import org.saar.rhi.blending.BlendState
import org.saar.rhi.depthstencil.CompareOp
import org.saar.rhi.depthstencil.DepthStencilState
import org.saar.rhi.opengl.blending.toOpengl
import org.saar.rhi.opengl.depthstencil.toOpengl
import org.saar.rhi.opengl.rasterization.toOpengl
import org.saar.rhi.opengl.shader.toOpengl
import org.saar.rhi.rasterization.CullMode
import org.saar.rhi.rasterization.RasterizationState
import org.saar.rhi.shader.GlslVersion
import org.saar.rhi.shader.ShaderModule
import org.saar.rhi.shader.ShaderModuleLoader
import org.saar.rhi.shader.ShaderProgram
import org.saar.rhi.shader.ShaderStage
import org.saar.rhi.shader.ShaderStageType
import kotlin.math.abs

private const val TRANSITION_TIME: Int = 1000

private fun FrustumIntersection.testChunk(chunk: Chunk) = testAab(
    chunk.bounds.min.x().toFloat(),
    chunk.bounds.min.y().toFloat(),
    chunk.bounds.min.z().toFloat(),
    chunk.bounds.max.x().toFloat() + 1,
    chunk.bounds.max.y().toFloat() + 1,
    chunk.bounds.max.z().toFloat() + 1
)

object ChunkRenderer {

    lateinit var atlas: Texture2D

    val rayCastedFace: Vec4iUniformValue
        get() = ChunkRendererPrototype.rayCastedFace

    private val prototype = ChunkRendererPrototype
    private val uniformsLoader = ShadersUniformsLoader.from(prototype)

    init {
        prototype.init()
    }

    private val rasterizationState = RasterizationState(
        cullMode = CullMode.BACK,
    ).toOpengl()

    private val depthStencilState = DepthStencilState(
        depthTestEnable = true,
        depthWriteEnable = true,
        depthCompareOp = CompareOp.LESS,
    ).toOpengl()

    private val blendState = BlendState().toOpengl()

    fun render(camera: ICamera, world: World) {
        val view = camera.viewMatrix
        val projection = camera.projection.matrix
        val frustumIntersection = FrustumIntersection(projection.mul(view, Matrix4.create()))

        prototype.shadersProgram.bind()
        rasterizationState.set()
        depthStencilState.set()
        blendState.set()
        ClipPlaneTest.disable()

        prototype.atlasUniform.value = this.atlas
        prototype.dimensionsUniform.value = Vector2i(16, 16)
        prototype.projectionViewUniform.value = projection.mul(view, Matrix4.create())
        prototype.normalMatrixUniform.value = view.invert(Matrix4.temp).transpose()

        val time = System.currentTimeMillis() % TRANSITION_TIME
        prototype.glowTransitionUniform.value = abs(time - TRANSITION_TIME / 2f) / TRANSITION_TIME / 2f

        world.chunks.filter { frustumIntersection.testChunk(it) }.forEach { chunk ->
            prototype.chunkCoordinateUniform.value = chunk.position
            uniformsLoader.load()
            chunk.drawOpaque()
        }
    }

    fun delete() {
        prototype.shadersProgram.delete()
        this.atlas.delete()
    }
}

private object ChunkRendererPrototype : ShadersLink {

    @UniformProperty
    val rayCastedFace = Vec4iUniformValue("u_rayCastedFace")

    @UniformProperty
    val projectionViewUniform = Mat4UniformValue("u_projectionView")

    @UniformProperty
    val normalMatrixUniform = Mat4UniformValue("u_normalMatrix")

    @UniformProperty
    val glowTransitionUniform = FloatUniformValue("u_glowTransition")

    @UniformProperty
    val atlasUniform = TextureUniformValue("u_atlas", 0)

    @UniformProperty
    val dimensionsUniform = Vec2iUniformValue("u_dimensions")

    @UniformProperty
    val chunkCoordinateUniform = Vec2iUniformValue("u_chunkCoordinate")

    override val vertexAttributes = arrayOf("in_data")

    override val fragmentOutputs = arrayOf("f_colour", "f_normalSpecular")

    override val shadersProgram = ShaderProgram(
        ShaderStage(
            type = ShaderStageType.VERTEX,
            module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + ShaderModuleLoader.loadSource("/minecraft/shaders/block.vertex.glsl"))
        ),
        ShaderStage(
            type = ShaderStageType.FRAGMENT,
            module = ShaderModule.fromString(GlslVersion.V400.toString() + "\n" + ShaderModuleLoader.loadSource("/minecraft/shaders/block.fragment.glsl"))
        ),
    ).toOpengl()
}
