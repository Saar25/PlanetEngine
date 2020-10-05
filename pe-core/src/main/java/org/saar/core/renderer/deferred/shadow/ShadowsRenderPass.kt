package org.saar.core.renderer.deferred.shadow

import org.joml.Matrix4f
import org.joml.Matrix4fc
import org.joml.Vector3fc
import org.saar.core.camera.ICamera
import org.saar.core.light.DirectionalLight
import org.saar.core.light.DirectionalLightUniform
import org.saar.core.renderer.*
import org.saar.core.renderer.deferred.DeferredRenderingBuffers
import org.saar.core.renderer.deferred.RenderPass
import org.saar.core.renderer.deferred.RenderPassBase
import org.saar.lwjgl.opengl.constants.RenderMode
import org.saar.lwjgl.opengl.objects.vaos.Vao
import org.saar.lwjgl.opengl.shaders.*
import org.saar.lwjgl.opengl.shaders.uniforms2.*
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture
import org.saar.lwjgl.opengl.utils.GlRendering
import org.saar.maths.utils.Matrix4

class ShadowsRenderPass(private val camera: ICamera,
                        private val shadowCamera: ICamera,
                        private val shadowMap: ReadOnlyTexture,
                        private val light: DirectionalLight)
    : RenderPassBase(shadersProgram), RenderPass {

    private var uniformsHelper = UniformsHelper.empty()
    private var stageUpdatersHelper = StageUpdatersHelper.empty()
    private var instanceUpdatersHelper = InstanceUpdatersHelper.empty<DeferredRenderingBuffers>()

    @UniformProperty
    private val shadowMatrixUniform = object : Mat4Uniform() {
        override fun getName(): String = "shadowMatrix"

        override fun getUniformValue(): Matrix4fc {
            return this@ShadowsRenderPass.shadowCamera.projection.matrix.mul(
                    this@ShadowsRenderPass.shadowCamera.viewMatrix, matrix)
        }
    }

    @UniformProperty
    private val projectionMatrixInvUniform = object : Mat4Uniform() {
        override fun getName(): String = "projectionMatrixInv"

        override fun getUniformValue(): Matrix4fc {
            return this@ShadowsRenderPass.camera.projection.matrix.invertPerspective(matrix)
        }
    }

    @UniformProperty
    private val viewMatrixInvUniform = object : Mat4Uniform() {
        override fun getName(): String = "viewMatrixInv"

        override fun getUniformValue(): Matrix4fc {
            return this@ShadowsRenderPass.camera.viewMatrix.invert(matrix)
        }
    }

    @UniformProperty
    private val cameraWorldPositionUniform = object : Vec3Uniform() {
        override fun getName(): String = "cameraWorldPosition"

        override fun getUniformValue(): Vector3fc {
            return this@ShadowsRenderPass.camera.transform.position.value
        }
    }

    @UniformProperty
    private val pcfRadiusUniform = object : IntUniform() {
        override fun getName(): String = "pcfRadius"

        override fun getUniformValue(): Int = 2
    }

    @UniformProperty
    private val lightUniform = object : DirectionalLightUniform("light") {
        override fun getUniformValue(): DirectionalLight {
            return this@ShadowsRenderPass.light
        }
    }

    @UniformProperty
    private val shadowMapUniform = object : TextureUniform() {
        override fun getName(): String = "shadowMap"

        override fun getUnit(): Int = 0

        override fun getUniformValue(): ReadOnlyTexture {
            return this@ShadowsRenderPass.shadowMap
        }
    }

    @UniformProperty
    private val colourTextureUniform = TextureUniformValue("colourTexture", 1)

    @UniformProperty
    private val normalTextureUniform = TextureUniformValue("normalTexture", 2)

    @UniformProperty
    private val depthTextureUniform = TextureUniformValue("depthTexture", 3)

    @UniformUpdater
    private val colourTextureUpdater = InstanceUniformUpdater<DeferredRenderingBuffers> { state ->
        this@ShadowsRenderPass.colourTextureUniform.value = state.instance.albedo
    }

    @UniformUpdater
    private val normalTextureUpdater = InstanceUniformUpdater<DeferredRenderingBuffers> { state ->
        this@ShadowsRenderPass.normalTextureUniform.value = state.instance.normal
    }

    @UniformUpdater
    private val depthTextureUpdater = InstanceUniformUpdater<DeferredRenderingBuffers> { state ->
        this@ShadowsRenderPass.depthTextureUniform.value = state.instance.depth
    }

    init {
        shadersProgram.bindFragmentOutputs("f_colour")

        this.uniformsHelper = buildHelper(uniformsHelper)
        this.stageUpdatersHelper = buildHelper(stageUpdatersHelper)
        this.instanceUpdatersHelper = buildHelper(instanceUpdatersHelper)
    }

    companion object {
        private val matrix: Matrix4f = Matrix4.create()

        private val vertex: Shader = Shader.createVertex(GlslVersion.V400,
                ShaderCode.loadSource("/shaders/deferred/quadVertex.glsl"))
        private val fragment: Shader = Shader.createFragment(GlslVersion.V400,
                ShaderCode.define("MAX_DIRECTIONAL_LIGHTS", "1"),
                ShaderCode.loadSource("/shaders/common/transform/transform.header.glsl"),
                ShaderCode.loadSource("/shaders/common/light/light.struct.glsl"),
                ShaderCode.loadSource("/shaders/common/light/light.header.glsl"),
                ShaderCode.loadSource("/shaders/deferred/shadow/fragment.glsl"),
                ShaderCode.loadSource("/shaders/common/light/light.source.glsl"),
                ShaderCode.loadSource("/shaders/common/transform/transform.source.glsl"))
        private val shadersProgram: ShadersProgram =
                ShadersProgram.create(vertex, fragment)
    }


    override fun onRender(buffers: DeferredRenderingBuffers) {
        val stage = StageRenderState()
        this.stageUpdatersHelper.update(stage)

        val instance = InstanceRenderState(buffers)
        this.instanceUpdatersHelper.update(instance)

        this.uniformsHelper.load()

        Vao.EMPTY.bind()
        GlRendering.drawArrays(RenderMode.TRIANGLE_STRIP, 0, 4)
    }
}