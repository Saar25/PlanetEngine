package org.saar.maths.transform

import org.joml.Matrix4f
import kotlin.test.Test
import kotlin.test.assertEquals

class NullTransformTest {

    @Test
    fun `transformation matrix is identity`() {
        val actual = NullTransform.transformationMatrix

        val expected = Matrix4f()
        for (i in 0 until 4) {
            for (j in 0 until 4) {
                val expectedValue = if (i == j) 1f else 0f
                assertEquals(expectedValue, actual.get(i, j), 0f, "cell ($i,$j)")
            }
        }
    }

    @Test
    fun `position is at the origin`() {
        assertEquals(0f, NullTransform.position.value.x(), 0f)
        assertEquals(0f, NullTransform.position.value.y(), 0f)
        assertEquals(0f, NullTransform.position.value.z(), 0f)
    }

    @Test
    fun `scale is one`() {
        assertEquals(1f, NullTransform.scale.value.x(), 0f)
        assertEquals(1f, NullTransform.scale.value.y(), 0f)
        assertEquals(1f, NullTransform.scale.value.z(), 0f)
    }
}
