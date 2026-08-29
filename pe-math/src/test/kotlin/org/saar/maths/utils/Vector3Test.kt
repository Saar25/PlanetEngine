package org.saar.maths.utils

import org.joml.Vector3f
import org.joml.Vector3fc
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Vector3Test {

    private fun givenVector3f(x: Float, y: Float, z: Float): Vector3fc = Vector3f(x, y, z)

    private fun assertVectorClose(expected: Vector3fc, actual: Vector3fc, delta: Float = 0.0001f) {
        assertEquals(expected.x(), actual.x(), delta)
        assertEquals(expected.y(), actual.y(), delta)
        assertEquals(expected.z(), actual.z(), delta)
    }

    @Test
    fun `create returns a zero vector`() {
        val actual = Vector3.create()

        assertVectorClose(givenVector3f(0f, 0f, 0f), actual, 0f)
    }

    @Test
    fun `of constructs a vector from its components`() {
        val actual = Vector3.of(1f, 2f, 3f)

        assertVectorClose(givenVector3f(1f, 2f, 3f), actual, 0f)
    }

    @Test
    fun `of constructs a vector with all components set to the same value`() {
        val actual = Vector3.of(4f)

        assertVectorClose(givenVector3f(4f, 4f, 4f), actual, 0f)
    }

    @Test
    fun `of returns a copy of the given vector`() {
        val source = Vector3f(1f, 2f, 3f)

        val actual = Vector3.of(source)
        source.set(9f, 9f, 9f)

        assertVectorClose(givenVector3f(1f, 2f, 3f), actual, 0f)
    }

    @Test
    fun `UP points along the positive y axis`() {
        assertVectorClose(givenVector3f(0f, 1f, 0f), Vector3.UP, 0f)
    }

    @Test
    fun `add sums the two vectors`() {
        val v1 = givenVector3f(1f, 2f, 3f)
        val v2 = givenVector3f(4f, 5f, 6f)

        val actual = Vector3.add(v1, v2)

        assertVectorClose(givenVector3f(5f, 7f, 9f), actual)
    }

    @Test
    fun `sub subtracts the second vector from the first`() {
        val v1 = givenVector3f(1f, 2f, 3f)
        val v2 = givenVector3f(4f, 5f, 6f)

        val actual = Vector3.sub(v1, v2)

        assertVectorClose(givenVector3f(-3f, -3f, -3f), actual)
    }

    @Test
    fun `mul multiplies the vector by a scalar`() {
        val v = givenVector3f(1f, 2f, 3f)

        val actual = Vector3.mul(v, 2f)

        assertVectorClose(givenVector3f(2f, 4f, 6f), actual)
    }

    @Test
    fun `div divides the vector by a scalar`() {
        val v = givenVector3f(2f, 4f, 6f)

        val actual = Vector3.div(v, 2f)

        assertVectorClose(givenVector3f(1f, 2f, 3f), actual)
    }

    @Test
    fun `cross computes the cross product`() {
        val v1 = givenVector3f(1f, 0f, 0f)
        val v2 = givenVector3f(0f, 1f, 0f)

        val actual = Vector3.cross(v1, v2)

        assertVectorClose(givenVector3f(0f, 0f, 1f), actual)
    }

    @Test
    fun `normalize returns a unit length vector`() {
        val actual = Vector3.normalize(0f, 3f, 4f)

        val length = kotlin.math.sqrt(actual.x() * actual.x() + actual.y() * actual.y() + actual.z() * actual.z())
        assertEquals(1f, length, 0.0001f)
    }

    @Test
    fun `length computes the magnitude of the vector`() {
        val actual = Vector3.length(3f, 4f, 0f)

        val expected = 5f
        assertEquals(expected, actual, 0.0001f)
    }

    @Test
    fun `randomize returns a vector with components in the range zero to one`() {
        val actual = Vector3.randomize()

        assertTrue(actual.x() >= 0f && actual.x() < 1f)
        assertTrue(actual.y() >= 0f && actual.y() < 1f)
        assertTrue(actual.z() >= 0f && actual.z() < 1f)
    }
}
