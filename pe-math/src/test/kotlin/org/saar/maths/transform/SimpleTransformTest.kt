package org.saar.maths.transform

import org.joml.Matrix4f
import org.saar.maths.utils.Vector3
import kotlin.test.Test
import kotlin.test.assertTrue

class SimpleTransformTest {

    @Test
    fun `transformation matrix is identity at creation`() {
        val transform = SimpleTransform()

        val actual = transform.transformationMatrix

        val expected = Matrix4f()
        assertTrue(actual.equals(expected, 0.0001f))
    }

    @Test
    fun `transformation matrix reflects the position`() {
        val transform = SimpleTransform()
        transform.position.set(5f, 0f, 0f)

        val actual = transform.transformationMatrix

        val expected = Matrix4f().translation(5f, 0f, 0f)
        assertTrue(actual.equals(expected, 0.0001f))
    }

    @Test
    fun `transformation matrix reflects the scale`() {
        val transform = SimpleTransform()
        transform.scale.set(2f, 2f, 2f)

        val actual = transform.transformationMatrix

        val expected = Matrix4f().scale(2f)
        assertTrue(actual.equals(expected, 0.0001f))
    }

    @Test
    fun `transformation matrix updates after a change to position`() {
        val transform = SimpleTransform()

        transform.position.add(Vector3.RIGHT)

        val actual = transform.transformationMatrix

        val expected = Matrix4f().translation(1f, 0f, 0f)
        assertTrue(actual.equals(expected, 0.0001f))
    }
}
