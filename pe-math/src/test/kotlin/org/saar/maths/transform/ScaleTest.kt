package org.saar.maths.transform

import org.joml.Vector3f
import org.joml.Vector3fc
import kotlin.test.Test
import kotlin.test.assertEquals

class ScaleTest {

    private fun givenVector3f(x: Float, y: Float, z: Float): Vector3fc = Vector3f(x, y, z)

    private fun assertVectorClose(expected: Vector3fc, actual: Vector3fc, delta: Float = 0.0001f) {
        assertEquals(expected.x(), actual.x(), delta)
        assertEquals(expected.y(), actual.y(), delta)
        assertEquals(expected.z(), actual.z(), delta)
    }

    @Test
    fun `create returns a scale of one`() {
        val actual = Scale.create()

        assertVectorClose(givenVector3f(1f, 1f, 1f), actual.value, 0f)
    }

    @Test
    fun `scale multiplies every component by the scalar`() {
        val scale = Scale.of(1f, 2f, 3f)

        scale.scale(2f)

        assertVectorClose(givenVector3f(2f, 4f, 6f), scale.value)
    }

    @Test
    fun `scale multiplies component wise by the vector`() {
        val scale = Scale.of(1f, 2f, 3f)

        scale.scale(givenVector3f(2f, 3f, 4f))

        assertVectorClose(givenVector3f(2f, 6f, 12f), scale.value)
    }

    @Test
    fun `scale multiplies component wise by the components`() {
        val scale = Scale.of(1f, 2f, 3f)

        scale.scale(2f, 3f, 4f)

        assertVectorClose(givenVector3f(2f, 6f, 12f), scale.value)
    }

    @Test
    fun `set overrides the value`() {
        val scale = Scale.of(1f, 1f, 1f)

        scale.set(4f)

        assertVectorClose(givenVector3f(4f, 4f, 4f), scale.value, 0f)
    }

    @Test
    fun `set from a vector overrides the value`() {
        val scale = Scale.of(1f, 1f, 1f)

        scale.set(givenVector3f(2f, 3f, 4f))

        assertVectorClose(givenVector3f(2f, 3f, 4f), scale.value, 0f)
    }
}
