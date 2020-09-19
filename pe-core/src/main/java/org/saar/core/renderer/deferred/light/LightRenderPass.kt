package org.saar.core.renderer.deferred.light

import org.joml.Matrix4f
import org.joml.Matrix4fc
import org.saar.core.camera.ICamera
import org.saar.core.light.DirectionalLight
import org.saar.core.light.DirectionalLightUniformProperty
import org.saar.core.renderer.AUniformProperty
import org.saar.core.renderer.UniformsHelper
import org.saar.core.renderer.deferred.RenderPass
import org.saar.core.renderer.deferred.RenderPassBase
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.objects.vaos.Vao
import org.saar.lwjgl.opengl.shaders.InstanceRenderState
import org.saar.lwjgl.opengl.shaders.Shader
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.StageRenderState
import org.saar.lwjgl.opengl.shaders.uniforms.UniformArrayProperty
import org.saar.lwjgl.opengl.shaders.uniforms.UniformIntProperty
import org.saar.lwjgl.opengl.shaders.uniforms.UniformMat4Property
import org.saar.lwjgl.opengl.shaders.uniforms.UniformTextureProperty
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.utils.GlRendering
import org.saar.lwjgl.opengl.utils.GlUtils
import org.saar.maths.utils.Matrix4

class LightRenderPass(private val camera: ICamera, private val input: LightRenderPassInput) : RenderPassBase(shadersProgram), RenderPass {

    private var uniformsHelper: UniformsHelper<ReadOnlyTexture> = UniformsHelper.empty()

    @AUniformProperty
    private val colourTextureUniform = object : UniformTextureProperty<LightRenderPassInstance>("colourTexture", 0) {
        override fun getInstanceValue(state: InstanceRenderState<LightRenderPassInstance>): ReadOnlyTexture {
            return state.instance.image
        }
    }

    @AUniformProperty
    private val normalTextureUniform = object : UniformTextureProperty<LightRenderPassInstance>("normalTexture", 1) {
        override fun getStageValue(state: StageRenderState): ReadOnlyTexture {
            return this@LightRenderPass.input.normalTexture
        }
    }

    @AUniformProperty
    private val depthTextureUniform = object : UniformTextureProperty<LightRenderPassInstance>("depthTexture", 2) {
        override fun getStageValue(state: StageRenderState): ReadOnlyTexture {
            return this@LightRenderPass.input.depthTexture
        }
    }

    @AUniformProperty
    private val projectionMatrixInvUniform = object : UniformMat4Property<LightRenderPassInstance>("projectionMatrixInv") {
        override fun getStageValue(state: StageRenderState): Matrix4fc {
            return this@LightRenderPass.camera.projection.matrix.invertPerspective(matrix)
        }
    }

    @AUniformProperty
    private val directionalLightsCountUniform = object : UniformIntProperty<LightRenderPassInstance>("directionalLightsCount") {
        override fun getStageValue(state: StageRenderState): Int {
            return 1
        }
    }

    @AUniformProperty
    private val directionalLightsUniform = UniformArrayProperty<DirectionalLight>("directionalLights", 2)
    { name, index -> DirectionalLightUniformProperty(name) }

    /*@AUniformProperty
    private val pointLightsCountUniform = object : UniformIntProperty<LightRenderPassInstance>("pointLightsCount") {
        override fun getStageValue(state: StageRenderState): Int {
            return 5
        }
    }

    @AUniformProperty
    private val pointLightsUniform = UniformArrayProperty<DirectionalLight>("pointLights", 2)
    { name, index -> DirectionalLightUniformProperty(name) }*/

    companion object {
        private val matrix: Matrix4f = Matrix4.create()

        private val vertex: Shader = Shader.createVertex(
                "/shaders/deferred/quadVertex.glsl")
        private val fragment: Shader = Shader.createFragment(
                "/shaders/deferred/light/fragment.glsl")
        private val shadersProgram: ShadersProgram =
                ShadersProgram.create(vertex, fragment)
    }

    init {
        shadersProgram.bindFragmentOutputs("f_colour")

        this.uniformsHelper = buildHelper(uniformsHelper)
    }

    override fun onRender(image: ReadOnlyTexture) {
        GlUtils.disableCulling()

        val stageState = StageRenderState()
        this.uniformsHelper.loadOnStage(stageState)

        val light = DirectionalLight()
        light.colour.set(1.0f, 1.0f, 1.0f)
        light.direction.set(-50f, -50f, -50f)

        val instance = LightRenderPassInstance(image, emptyArray(), arrayOf(light))
        val instanceState = InstanceRenderState(instance)
        this.colourTextureUniform.loadOnInstance(instanceState)

        val state = InstanceRenderState<DirectionalLight>(light)
        this.directionalLightsUniform.loadOnInstance(state)

        // TODO: bind some default vao, cannot draw without bound vao!
        Vao.EMPTY.bind()
        GlRendering.drawArrays(RenderMode.TRIANGLE_STRIP, 0, 4)
    }
}