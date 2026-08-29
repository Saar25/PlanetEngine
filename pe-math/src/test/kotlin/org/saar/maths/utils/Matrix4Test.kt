package org.saar.maths.utils

import org.joml.Matrix4f
import org.joml.Quaternionf
import org.joml.Vector3f
import org.saar.maths.utils.Matrix4.ofProjection
import org.saar.maths.utils.Matrix4.ofTransformation
import org.saar.maths.utils.Matrix4.ofView
import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix4Test {

    private fun givenScaledMatrix(scale: Float): Matrix4f =
        Matrix4f().scale(scale)

    @Test
    fun `create returns an identity matrix`() {
        val actual = Matrix4.create()

        val expected = Matrix4f()
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                val expectedValue = if (i == j) 1f else 0f
                assertEquals(expectedValue, actual.get(i, j), 0f, "cell ($i,$j)")
            }
        }
    }

    @Test
    fun `of returns a copy of the given matrix`() {
        val source = givenScaledMatrix(2f)

        val actual = Matrix4.of(source)
        source.identity()

        assertEquals(2f, actual.m00(), 0f)
        assertEquals(2f, actual.m11(), 0f)
        assertEquals(2f, actual.m22(), 0f)
    }

    @Test
    fun `ofProjection produces a perspective matrix with the expected fov factor`() {
        val fov = (PI / 2).toFloat()
        val width = 800f
        val height = 600f

        val actual = Matrix4.create().ofProjection(fov, width, height, 0.1f, 100f)

        val expected = Matrix4f().setPerspective(fov, width / height, 0.1f, 100f)
        assertEquals(expected.m00(), actual.m00(), 0.0001f)
        assertEquals(expected.m11(), actual.m11(), 0.0001f)
    }

    @Test
    fun `ofProjection produces an orthographic matrix mapping the range`() {
        val actual = Matrix4.create().ofProjection(-1f, 1f, -1f, 1f, 0f, 10f)

        val expected = Matrix4f().setOrtho(-1f, 1f, -1f, 1f, 0f, 10f)
        assertEquals(expected.m00(), actual.m00(), 0.0001f)
        assertEquals(expected.m11(), actual.m11(), 0.0001f)
        assertEquals(expected.m22(), actual.m22(), 0.0001f)
    }

    @Test
    fun `ofView inverts translation and rotation`() {
        val position = Vector3f(10f, 20f, 30f)
        val rotation = Quaternionf()

        val actual = Matrix4.create().ofView(position, rotation)

        val expected = Matrix4f().translationRotateScaleInvert(position, rotation, 1f)
        assertEquals(expected.m30(), actual.m30(), 0.0001f)
        assertEquals(expected.m31(), actual.m31(), 0.0001f)
        assertEquals(expected.m32(), actual.m32(), 0.0001f)
    }

    @Test
    fun `ofTransformation builds a matrix from position rotation and scale`() {
        val position = Vector3f(1f, 2f, 3f)
        val rotation = Quaternionf()
        val scale = Vector3f(2f, 2f, 2f)

        val actual = Matrix4.create().ofTransformation(position, rotation, scale)

        val expected = Matrix4f().translationRotateScale(position, rotation, scale)
        assertEquals(expected.m00(), actual.m00(), 0.0001f)
        assertEquals(expected.m11(), actual.m11(), 0.0001f)
        assertEquals(expected.m30(), actual.m30(), 0.0001f)
        assertEquals(expected.m31(), actual.m31(), 0.0001f)
        assertEquals(expected.m32(), actual.m32(), 0.0001f)
    }
}
