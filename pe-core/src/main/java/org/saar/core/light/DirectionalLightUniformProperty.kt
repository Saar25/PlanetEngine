package org.saar.core.light

import org.joml.Vector3fc
import org.saar.lwjgl.opengl.shaders.InstanceRenderState
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.StageRenderState
import org.saar.lwjgl.opengl.shaders.uniforms.UniformProperty
import org.saar.lwjgl.opengl.shaders.uniforms.UniformVec3Property

open class DirectionalLightUniformProperty(private val name: String) : UniformProperty<DirectionalLight> {

    private val directionUniform = object : UniformVec3Property<DirectionalLight>("$name.direction") {
        override fun getInstanceValue(state: InstanceRenderState<DirectionalLight>): Vector3fc {
            return state.instance.direction.normalize()
        }
    }

    private val colourUniform = object : UniformVec3Property<DirectionalLight>("$name.colour") {
        override fun getInstanceValue(state: InstanceRenderState<DirectionalLight>): Vector3fc {
            return state.instance.colour
        }
    }

    override fun initialize(shadersProgram: ShadersProgram) {
        this.directionUniform.initialize(shadersProgram)
        this.colourUniform.initialize(shadersProgram)
    }

    override fun loadOnStage(state: StageRenderState) {
        this.directionUniform.loadOnStage(state)
        this.colourUniform.loadOnStage(state)
    }

    override fun loadOnInstance(state: InstanceRenderState<DirectionalLight>) {
        this.directionUniform.loadOnInstance(state)
        this.colourUniform.loadOnInstance(state)
    }
}