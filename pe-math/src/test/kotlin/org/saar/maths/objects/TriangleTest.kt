package org.saar.maths.objects

import org.joml.Vector2f
import org.joml.Vector3f
import org.joml.Vector3fc
import org.saar.maths.utils.Vector3
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertSame
import kotlin.test.assertTrue

class TriangleTest {

    private fun givenVector3f(x: Float, y: Float, z: Float): Vector3fc = Vector3f(x, y, z)

    private fun givenXzTriangle(): Triangle = Triangle(
        givenVector3f(0f, 0f, 0f),
        givenVector3f(1f, 0f, 0f),
        givenVector3f(0f, 0f, 1f)
    )

    private fun givenFlatTriangleAtY(y: Float): Triangle = Triangle(
        givenVector3f(0f, y, 0f),
        givenVector3f(1f, y, 0f),
        givenVector3f(0f, y, 1f)
    )

    @Test
    fun `contains returns true for a point inside the triangle`() {
        val triangle = givenXzTriangle()
        val point = givenVector3f(0.25f, 0f, 0.25f)

        assertTrue(triangle.contains(point))
    }

    @Test
    fun `contains returns false for a point outside the triangle`() {
        val triangle = givenXzTriangle()
        val point = givenVector3f(1f, 0f, 1f)

        assertFalse(triangle.contains(point))
    }

    @Test
    fun `contains xz returns true for a position inside the triangle`() {
        val triangle = givenXzTriangle()

        assertTrue(triangle.contains(0.25f, 0.25f))
    }

    @Test
    fun `contains xz returns false for a position outside the triangle`() {
        val triangle = givenXzTriangle()

        assertFalse(triangle.contains(1f, 1f))
    }

    @Test
    fun `getHeight returns the flat y for any position on the triangle`() {
        val triangle = givenFlatTriangleAtY(5f)
        val position = Vector2f(0.25f, 0.25f)

        val actual = triangle.getHeight(position)

        val expected = 5f
        assertEquals(expected, actual)
    }

    @Test
    fun `toSpace returns the same instance for a unit space`() {
        val triangle = givenXzTriangle()

        val actual = triangle.toSpace(Vector3.ONE)

        assertSame(triangle, actual)
    }

    @Test
    fun `toSpace divides the vertices by the space`() {
        val triangle = Triangle(
            givenVector3f(2f, 0f, 0f),
            givenVector3f(4f, 0f, 0f),
            givenVector3f(0f, 0f, 2f)
        )
        val space = givenVector3f(2f, 2f, 2f)

        val actual = triangle.toSpace(space)

        assertEquals(1f, actual.p1.x(), 0.0001f)
        assertEquals(2f, actual.p2.x(), 0.0001f)
        assertEquals(1f, actual.p3.z(), 0.0001f)
    }
}
