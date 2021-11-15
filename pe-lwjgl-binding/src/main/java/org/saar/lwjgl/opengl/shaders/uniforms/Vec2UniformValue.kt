package org.saar.lwjgl.opengl.shaders.uniforms

import org.joml.Vector2f
import org.joml.Vector2fc
import org.saar.maths.JomlDelegates

class Vec2UniformValue(
    override val name: String,
    initialValue: Vector2fc = Vector2f(0f, 0f)
) : Vec2Uniform() {
    override var value: Vector2fc by JomlDelegates.CachedVector2f(Vector2f(initialValue))
}