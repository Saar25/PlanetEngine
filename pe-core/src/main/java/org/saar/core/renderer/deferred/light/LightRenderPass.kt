package org.saar.core.renderer.deferred.light

import org.joml.Matrix4f
import org.joml.Matrix4fc
import org.joml.Vector3fc
import org.saar.core.camera.ICamera
import org.saar.core.light.DirectionalLight
import org.saar.core.light.DirectionalLightUniformProperty
import org.saar.core.light.PointLight
import org.saar.core.renderer.AUniformProperty
import org.saar.core.renderer.UniformsHelper
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.deferred.RenderPass
import org.saar.core.renderer.deferred.RenderPassBase
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.objects.vaos.Vao
import org.saar.lwjgl.opengl.shaders.*
import org.saar.lwjgl.opengl.shaders.uniforms.*
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.utils.GlRendering
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class LightRenderPass(private val camera: ICamera) : RenderPassBase(shadersProgram), RenderPass {

    private var uniformsHelper: UniformsHelper<PerInstance> = UniformsHelper.empty()

    @AUniformProperty
    private val colourTextureUniform = object : UniformTextureProperty.Instance<PerInstance>("colourTexture", 0) {
        override fun getUniformValue(state: InstanceRenderState<PerInstance>): ReadOnlyTexture {
            return state.instance.buffers.albedo
        }
    }

    @AUniformProperty
    private val normalTextureUniform = object : UniformTextureProperty.Instance<PerInstance>("normalTexture", 1) {
        override fun getUniformValue(state: InstanceRenderState<PerInstance>): ReadOnlyTexture {
            return state.instance.buffers.normal
        }
    }

    @AUniformProperty
    private val depthTextureUniform = object : UniformTextureProperty.Instance<PerInstance>("depthTexture", 2) {
        override fun getUniformValue(state: InstanceRenderState<PerInstance>): ReadOnlyTexture {
            return state.instance.buffers.depth
        }
    }

    @AUniformProperty
    private val cameraWorldPositionUniform = object : UniformVec3Property.Stage("cameraWorldPosition") {
        override fun getUniformValue(state: StageRenderState): Vector3fc {
            return this@LightRenderPass.camera.transform.position.value
        }
    }

    @AUniformProperty
    private val projectionMatrixInvUniform = object : UniformMat4Property.Stage("projectionMatrixInv") {
        override fun getUniformValue(state: StageRenderState): Matrix4fc {
            return this@LightRenderPass.camera.projection.matrix.invertPerspective(matrix)
        }
    }

    @AUniformProperty
    private val viewMatrixInvUniform = object : UniformMat4Property.Stage("viewMatrixInv") {
        override fun getUniformValue(state: StageRenderState): Matrix4fc {
            return this@LightRenderPass.camera.viewMatrix.invert(matrix)
        }
    }

    @AUniformProperty
    private val directionalLightsCountUniform = object : UniformIntProperty.Instance<PerInstance>(
            "directionalLightsCount") {
        override fun getUniformValue(state: InstanceRenderState<PerInstance>): Int {
            return state.instance.directionalLights.size
        }
    }

    @AUniformProperty
    private val directionalLightsUniform = UniformArrayProperty.Instance<PerInstance, DirectionalLight>(
            "directionalLights", 1) { name, index ->
        object : InstanceUniform<PerInstance, DirectionalLight>(DirectionalLightUniformProperty(name)) {
            override fun getUniformValue(state: InstanceRenderState<PerInstance>): DirectionalLight {
                return state.instance.directionalLights[index]
            }
        }
    }

    companion object {
        private val matrix: Matrix4f = Matrix4.create()

        private val vertex: Shader = Shader.createVertex(GlslVersion.V400,
                "/shaders/deferred/quadVertex.glsl")
        private val fragment: Shader = Shader.createFragment(GlslVersion.V400,
                "/shaders/deferred/light/fragment.glsl",
                "/shaders/common/light/light.source.glsl",
                "/shaders/common/transform/transform.source.glsl")
        private val shadersProgram: ShadersProgram =
                ShadersProgram.create(vertex, fragment)
    }

    init {
        shadersProgram.bindFragmentOutputs("f_colour")

        this.uniformsHelper = buildHelper(uniformsHelper)
    }

    override fun onRender(buffers: DeferredRenderingBuffers) {
        GlUtils.disableCulling()

        val stageState = StageRenderState()
        this.uniformsHelper.loadOnStage(stageState)

        val light = DirectionalLight()
                .also { light -> light.colour.set(1.0f, 1.0f, 1.0f) }
                .also { light -> light.direction.set(-50f, -50f, -50f) }

        val instance = PerInstance(buffers, emptyArray(), arrayOf(light))
        val instanceState = InstanceRenderState(instance)
        uniformsHelper.loadOnInstance(instanceState)

        // TODO: bind some default vao, cannot draw without bound vao!
        Vao.EMPTY.bind()
        GlRendering.drawArrays(RenderMode.TRIANGLE_STRIP, 0, 4)
    }

    private class PerInstance(
            val buffers: DeferredRenderingBuffers,
            val pointLights: Array<PointLight>,
            val directionalLights: Array<DirectionalLight>)
}