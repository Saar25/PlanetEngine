package org.saar.lwjgl.opengl.shader.uniforms

class UniformArray<T : UniformContainer>(
    name: String, size: Int, supplier: (String) -> T
) : UniformContainer {

    val value = (0 until size).map { supplier("$name[$it]") }

    override val subUniforms = this.value.flatMap { it.subUniforms }
}