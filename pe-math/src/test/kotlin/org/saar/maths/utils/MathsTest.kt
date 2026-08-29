package org.saar.maths.utils

import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector3fc
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MathsTest {

    private fun givenVector3f(x: Float, y: Float, z: Float): Vector3fc = Vector3f(x, y, z)

    private fun givenVector2f(x: Float, y: Float): Vector2f = Vector2f(x, y)

    @Test
    fun `barycentric returns the points y when the triangle is flat and the position is a vertex`() {
        val p1 = givenVector3f(0f, 5f, 0f)
        val p2 = givenVector3f(1f, 5f, 0f)
        val p3 = givenVector3f(0f, 5f, 1f)
        val ps = givenVector2f(0f, 0f)

        val actual = Maths.barycentric(p1, p2, p3, ps)

        val expected = 5f
        assertEquals(expected, actual)
    }

    @Test
    fun `barycentric returns the flat y for any position inside the triangle`() {
        val p1 = givenVector3f(0f, 5f, 0f)
        val p2 = givenVector3f(1f, 5f, 0f)
        val p3 = givenVector3f(0f, 5f, 1f)
        val ps = givenVector2f(0.25f, 0.25f)

        val actual = Maths.barycentric(p1, p2, p3, ps)

        val expected = 5f
        assertEquals(expected, actual)
    }

    @Test
    fun `calculateNormal returns the normalized cross product of the two triangle edges`() {
        val p1 = givenVector3f(0f, 0f, 0f)
        val p2 = givenVector3f(1f, 0f, 0f)
        val p3 = givenVector3f(0f, 1f, 0f)

        val actual = Maths.calculateNormal(p1, p2, p3)

        val expected = givenVector3f(0f, 0f, 1f)
        assertEquals(expected.x(), actual.x, 0.0001f)
        assertEquals(expected.y(), actual.y, 0.0001f)
        assertEquals(expected.z(), actual.z, 0.0001f)
    }

    @Test
    fun `clamp returns the value when it is inside the range`() {
        val actual = Maths.clamp(5f, 0f, 10f)

        val expected = 5f
        assertEquals(expected, actual)
    }

    @Test
    fun `clamp returns the minimum when the value is below the range`() {
        val actual = Maths.clamp(-3f, 0f, 10f)

        val expected = 0f
        assertEquals(expected, actual)
    }

    @Test
    fun `clamp returns the maximum when the value is above the range`() {
        val actual = Maths.clamp(15f, 0f, 10f)

        val expected = 10f
        assertEquals(expected, actual)
    }

    @Test
    fun `clamp int returns the value when it is inside the range`() {
        val actual = Maths.clamp(5, 0, 10)

        val expected = 5
        assertEquals(expected, actual)
    }

    @Test
    fun `clamp int returns the minimum when the value is below the range`() {
        val actual = Maths.clamp(-3, 0, 10)

        val expected = 0
        assertEquals(expected, actual)
    }

    @Test
    fun `clamp int returns the maximum when the value is above the range`() {
        val actual = Maths.clamp(15, 0, 10)

        val expected = 10
        assertEquals(expected, actual)
    }

    @Test
    fun `isBetween returns true for a value strictly inside the range`() {
        val actual = Maths.isBetween(5f, 0f, 10f)

        assertTrue(actual)
    }

    @Test
    fun `isBetween returns false for a value equal to the minimum`() {
        val actual = Maths.isBetween(0f, 0f, 10f)

        assertFalse(actual)
    }

    @Test
    fun `isBetween returns false for a value equal to the maximum`() {
        val actual = Maths.isBetween(10f, 0f, 10f)

        assertFalse(actual)
    }

    @Test
    fun `isInside returns true for a value equal to a bound`() {
        val actual = Maths.isInside(0f, 0f, 10f)

        assertTrue(actual)
    }

    @Test
    fun `isInside returns true for a value inside the range`() {
        val actual = Maths.isInside(5f, 0f, 10f)

        assertTrue(actual)
    }

    @Test
    fun `isInside returns false for a value outside the range`() {
        val actual = Maths.isInside(-1f, 0f, 10f)

        assertFalse(actual)
    }

    @Test
    fun `mix float returns the first value for scalar zero`() {
        val actual = Maths.mix(2f, 8f, 0f)

        val expected = 2f
        assertEquals(expected, actual)
    }

    @Test
    fun `mix float returns the second value for scalar one`() {
        val actual = Maths.mix(2f, 8f, 1f)

        val expected = 8f
        assertEquals(expected, actual)
    }

    @Test
    fun `mix float interpolates between the two values for a scalar in the middle`() {
        val actual = Maths.mix(2f, 8f, 0.25f)

        val expected = 3.5f
        assertEquals(expected, actual)
    }

    @Test
    fun `mix vector returns the second vector for scalar one`() {
        val vec1 = givenVector3f(1f, 2f, 3f)
        val vec2 = givenVector3f(5f, 6f, 7f)

        val actual = Maths.mix(vec1, vec2, 1f)

        val expected = givenVector3f(5f, 6f, 7f)
        assertEquals(expected.x(), actual.x, 0.0001f)
        assertEquals(expected.y(), actual.y, 0.0001f)
        assertEquals(expected.z(), actual.z, 0.0001f)
    }

    @Test
    fun `mix vector interpolates component wise for a scalar in the middle`() {
        val vec1 = givenVector3f(1f, 2f, 3f)
        val vec2 = givenVector3f(5f, 6f, 7f)

        val actual = Maths.mix(vec1, vec2, 0.5f)

        val expected = givenVector3f(3f, 4f, 5f)
        assertEquals(expected.x(), actual.x, 0.0001f)
        assertEquals(expected.y(), actual.y, 0.0001f)
        assertEquals(expected.z(), actual.z, 0.0001f)
    }

    @Test
    fun `sinf returns the sine of a float angle`() {
        val actual = Maths.sinf((PI / 2).toFloat())

        val expected = 1f
        assertEquals(expected, actual, 0.0001f)
    }

    @Test
    fun `sinf double returns the sine of a double angle`() {
        val actual = Maths.sinf(PI / 2)

        val expected = 1.0
        assertEquals(expected, actual.toDouble(), 0.0001)
    }

    @Test
    fun `cosf returns the cosine of a float angle`() {
        val actual = Maths.cosf(0f)

        val expected = 1f
        assertEquals(expected, actual, 0.0001f)
    }

    @Test
    fun `cosf double returns the cosine of a double angle`() {
        val actual = Maths.cosf(0.0)

        val expected = 1.0
        assertEquals(expected, actual.toDouble(), 0.0001)
    }

    @Test
    fun `tanf returns the tangent of a float angle`() {
        val actual = Maths.tanf(0f)

        val expected = 0f
        assertEquals(expected, actual, 0.0001f)
    }

    @Test
    fun `tanf double returns the tangent of a double angle`() {
        val actual = Maths.tanf(0.0)

        val expected = 0.0
        assertEquals(expected, actual.toDouble(), 0.0001)
    }

    @Test
    fun `sqrt returns the square root of a perfect square`() {
        val actual = Maths.sqrt(9f)

        val expected = 3f
        assertEquals(expected, actual)
    }

    @Test
    fun `sqrt returns the square root of a non perfect square`() {
        val actual = Maths.sqrt(2f)

        val expected = 1.4142135f
        assertEquals(expected, actual, 0.0001f)
    }
}
