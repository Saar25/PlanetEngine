package org.saar.lwjgl.opengl.shaders.uniforms

import org.joml.Vector4f
import org.joml.Vector4fc
import org.saar.maths.JomlDelegates

class Vec4UniformValue(
    override val name: String,
    initialValue: Vector4fc = Vector4f(0f, 0f, 0f, 0f)
) : Vec4Uniform() {
    override val value: Vector4f by JomlDelegates.CachedVector4f(Vector4f(initialValue))
}