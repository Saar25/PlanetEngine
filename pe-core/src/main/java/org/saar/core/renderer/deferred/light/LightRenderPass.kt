package org.saar.core.renderer.deferred.light

import org.joml.Matrix4f
import org.joml.Matrix4fc
import org.joml.Vector3fc
import org.saar.core.camera.ICamera
import org.saar.core.light.DirectionalLight
import org.saar.core.light.DirectionalLightUniformValue
import org.saar.core.light.IDirectionalLight
import org.saar.core.light.PointLight
import org.saar.core.renderer.*
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.deferred.RenderPass
import org.saar.core.renderer.deferred.RenderPassBase
import org.saar.core.renderer.shaders.ShaderProperty
import org.saar.core.renderer.uniforms.*
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.objects.vaos.Vao
import org.saar.lwjgl.opengl.shaders.*
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.lwjgl.opengl.utils.GlCullFace
import org.saar.lwjgl.opengl.utils.GlRendering
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class LightRenderPass(private val camera: ICamera) : RenderPassBase(), RenderPass {

    private var uniformsHelper = UniformsHelper.empty()
    private var instanceUpdatersHelper = UpdatersHelper.empty<PerInstance>()

    @UniformProperty
    private val colourTextureUniform = TextureUniformValue("colourTexture", 0)

    @UniformProperty
    private val normalTextureUniform = TextureUniformValue("normalTexture", 1)

    @UniformProperty
    private val depthTextureUniform = TextureUniformValue("depthTexture", 2)

    @UniformUpdaterProperty
    private val colourTextureUpdater =
        UniformUpdater<PerInstance> { state ->
            this@LightRenderPass.colourTextureUniform.value = state.instance.buffers.albedo
        }

    @UniformUpdaterProperty
    private val normalTextureUpdater =
        UniformUpdater<PerInstance> { state ->
            this@LightRenderPass.normalTextureUniform.value = state.instance.buffers.normal
        }

    @UniformUpdaterProperty
    private val depthTextureUpdater =
        UniformUpdater<PerInstance> { state ->
            this@LightRenderPass.depthTextureUniform.value = state.instance.buffers.depth
        }

    @UniformProperty
    private val cameraWorldPositionUniform = object : Vec3Uniform() {
        override fun getName(): String = "cameraWorldPosition"

        override fun getUniformValue(): Vector3fc {
            return this@LightRenderPass.camera.transform.position.value
        }
    }

    @UniformProperty
    private val projectionMatrixInvUniform = object : Mat4Uniform() {
        override fun getName(): String = "projectionMatrixInv"

        override fun getUniformValue(): Matrix4fc {
            return this@LightRenderPass.camera.projection.matrix.invertPerspective(matrix)
        }
    }

    @UniformProperty
    private val viewMatrixInvUniform = object : Mat4Uniform() {
        override fun getName(): String = "viewMatrixInv"

        override fun getUniformValue(): Matrix4fc {
            return this@LightRenderPass.camera.viewMatrix.invert(matrix)
        }
    }

    @UniformProperty
    private val directionalLightsCountUniform = IntUniformValue("directionalLightsCount")

    @UniformUpdaterProperty
    private val directionalLightsCountUpdater =
        UniformUpdater<PerInstance> { state ->
            directionalLightsCountUniform.value = state.instance.directionalLights.size
        }

    @UniformProperty
    private val directionalLightsUniform2 = WritableUniformArray<IDirectionalLight>("directionalLights", 1)
    { name, _ -> DirectionalLightUniformValue(name) }

    @UniformUpdaterProperty
    private val directionalLightsUpdater =
        UniformUpdater<PerInstance> { state ->
            this@LightRenderPass.directionalLightsUniform2.setValue(state.instance.directionalLights)
        }

    @ShaderProperty(ShaderType.VERTEX)
    private val vertexShader: Shader = Shader.createVertex(GlslVersion.V400,
        ShaderCode.loadSource("/shaders/deferred/quadVertex.glsl"))

    @ShaderProperty(ShaderType.FRAGMENT)
    private val fragmentShader: Shader = Shader.createFragment(GlslVersion.V400,
        ShaderCode.define("MAX_POINT_LIGHTS", "5"),
        ShaderCode.define("MAX_DIRECTIONAL_LIGHTS", "2"),

        ShaderCode.loadSource("/shaders/common/light/light.struct.glsl"),
        ShaderCode.loadSource("/shaders/common/light/light.header.glsl"),
        ShaderCode.loadSource("/shaders/common/transform/transform.header.glsl"),

        ShaderCode.loadSource("/shaders/deferred/light/fragment.glsl"),

        ShaderCode.loadSource("/shaders/common/light/light.source.glsl"),
        ShaderCode.loadSource("/shaders/common/transform/transform.source.glsl"))

    companion object {
        private val matrix: Matrix4f = Matrix4.create()
    }

    init {
        buildShadersProgram()
        bindFragmentOutputs("f_colour")

        this.uniformsHelper = buildHelper(uniformsHelper)
        this.instanceUpdatersHelper = buildHelper(instanceUpdatersHelper)
    }

    override fun onRender(buffers: DeferredRenderingBuffers) {
        GlUtils.setCullFace(GlCullFace.NONE)

        val light = DirectionalLight()
            .also { light -> light.colour.set(1.0f, 1.0f, 1.0f) }
            .also { light -> light.direction.set(-50f, -50f, -50f) }

        val instance = PerInstance(buffers, emptyArray(), arrayOf(light))
        val instanceState = RenderState(instance)
        this.instanceUpdatersHelper.update(instanceState)

        this.uniformsHelper.load()

        Vao.EMPTY.bind()
        GlRendering.drawArrays(RenderMode.TRIANGLE_STRIP, 0, 4)
    }

    private class PerInstance(
        val buffers: DeferredRenderingBuffers,
        val pointLights: Array<PointLight>,
        val directionalLights: Array<DirectionalLight>)
}