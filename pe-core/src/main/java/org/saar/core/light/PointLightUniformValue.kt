package org.saar.core.light

import org.saar.lwjgl.opengl.shaders.uniforms.UniformValue

class PointLightUniformValue(name: String) : PointLightUniform(name), UniformValue<IPointLight> {

    private val value: PointLight = PointLight()

    override fun setValue(value: IPointLight) {
        this.value.position.set(value.position)
        this.value.attenuation = value.attenuation
        this.value.colour.set(value.colour)
    }

    override fun getValue(): IPointLight {
        return this.value
    }

    override fun getUniformValue(): IPointLight {
        return this.value
    }
}