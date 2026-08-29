package org.saar.maths.objects

import org.joml.Vector3f
import org.joml.Vector3fc
import org.saar.maths.utils.Vector3
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class EllipsoidTest {

    private fun givenVector3f(x: Float, y: Float, z: Float): Vector3fc = Vector3f(x, y, z)

    private fun assertVectorClose(expected: Vector3fc, actual: Vector3fc, delta: Float = 0.0001f) {
        assertEquals(expected.x(), actual.x(), delta)
        assertEquals(expected.y(), actual.y(), delta)
        assertEquals(expected.z(), actual.z(), delta)
    }

    @Test
    fun `default constructor creates an ellipsoid at the origin with unit dimensions`() {
        val ellipsoid = Ellipsoid()

        assertVectorClose(givenVector3f(0f, 0f, 0f), ellipsoid.position, 0f)
        assertVectorClose(givenVector3f(1f, 1f, 1f), ellipsoid.dimensions, 0f)
    }

    @Test
    fun `constructor stores the given position and dimensions`() {
        val ellipsoid = Ellipsoid(givenVector3f(1f, 2f, 3f), givenVector3f(4f, 5f, 6f))

        assertVectorClose(givenVector3f(1f, 2f, 3f), ellipsoid.position, 0f)
        assertVectorClose(givenVector3f(4f, 5f, 6f), ellipsoid.dimensions, 0f)
    }

    @Test
    fun `toSpace returns the same instance for a unit space`() {
        val ellipsoid = Ellipsoid(givenVector3f(1f, 2f, 3f), givenVector3f(4f, 5f, 6f))

        val actual = ellipsoid.toSpace(Vector3.ONE)

        assertSame(ellipsoid, actual)
    }

    @Test
    fun `toSpace divides position and dimensions by the space`() {
        val ellipsoid = Ellipsoid(givenVector3f(2f, 4f, 6f), givenVector3f(8f, 10f, 12f))
        val space = givenVector3f(2f, 2f, 2f)

        val actual = ellipsoid.toSpace(space)

        assertVectorClose(givenVector3f(1f, 2f, 3f), actual.position)
        assertVectorClose(givenVector3f(4f, 5f, 6f), actual.dimensions)
    }
}
