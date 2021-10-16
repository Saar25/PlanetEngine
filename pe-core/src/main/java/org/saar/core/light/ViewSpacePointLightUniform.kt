package org.saar.core.light

import org.saar.core.camera.ICamera
import org.saar.lwjgl.opengl.shaders.ShadersProgram
import org.saar.lwjgl.opengl.shaders.uniforms.Uniform
import org.saar.lwjgl.opengl.shaders.uniforms.Vec3UniformValue
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Vector3
import org.saar.maths.utils.Vector4

abstract class ViewSpacePointLightUniform(name: String) : Uniform {

    var camera: ICamera? = null

    private val positionUniform = Vec3UniformValue("$name.position")
    private val attenuationUniform = Vec3UniformValue("$name.attenuation")
    private val colourUniform = Vec3UniformValue("$name.colour")

    final override fun initialize(shadersProgram: ShadersProgram) {
        this.positionUniform.initialize(shadersProgram)
        this.attenuationUniform.initialize(shadersProgram)
        this.colourUniform.initialize(shadersProgram)
    }

    final override fun load() {
        val value = getUniformValue()

        val vs = Vector4.of(value.position, 1f).mul(this.camera!!.viewMatrix)
        this.positionUniform.value = Vector3.of(vs).div(vs.w())

        this.attenuationUniform.value = value.attenuation.vector3f
        this.colourUniform.value = value.colour

        this.positionUniform.load()
        this.attenuationUniform.load()
        this.colourUniform.load()
    }

    abstract fun getUniformValue(): IPointLight
}