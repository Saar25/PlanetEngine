package org.saar.core.light

import org.saar.lwjgl.opengl.shaders.InstanceRenderState
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.StageRenderState
import org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.uniforms.UniformVec3Property

open class DirectionalLightUniformProperty(private val name: String) : UniformProperty<DirectionalLight> {

    val directionUniform = UniformVec3Property("$name.direction")

    val colourUniform = UniformVec3Property("$name.colour")

    override fun initialize(shadersProgram: ShadersProgram) {
        this.directionUniform.initialize(shadersProgram)
        this.colourUniform.initialize(shadersProgram)
    }

    override fun loadValue(value: DirectionalLight) {
        this.directionUniform.loadValue(value.direction.normalize())
        this.colourUniform.loadValue(value.colour)
    }

    abstract class Stage(name: String) : DirectionalLightUniformProperty(name), UniformProperty.Stage<DirectionalLight> {
        override fun loadOnStage(state: StageRenderState) {
            loadValue(getUniformValue(state))
        }

        abstract fun getUniformValue(state: StageRenderState): DirectionalLight
    }

    abstract class Instance<T>(name: String) : DirectionalLightUniformProperty(name), UniformProperty.Instance<T, DirectionalLight> {
        override fun loadOnInstance(state: InstanceRenderState<T>) {
            loadValue(getUniformValue(state))
        }

        abstract fun getUniformValue(state: InstanceRenderState<T>): DirectionalLight
    }
}