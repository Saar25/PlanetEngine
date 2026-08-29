package org.saar.maths.transform

import kotlin.test.Test
import kotlin.test.assertEquals

class RelativeTransformTest {

    @Test
    fun `with identity bases the matrix equals the source transformation`() {
        val transform = SimpleTransform()
        transform.position.set(5f, 0f, 0f)
        val from = NullTransform
        val to = NullTransform

        val relative = RelativeTransform(transform, from, to)
        val actual = relative.transformationMatrix

        assertEquals(5f, actual.m30(), 0.0001f)
        assertEquals(0f, actual.m31(), 0.0001f)
        assertEquals(0f, actual.m32(), 0.0001f)
    }

    @Test
    fun `the to basis translation is applied to the relative matrix`() {
        val transform = SimpleTransform()
        val from = NullTransform
        val to = SimpleTransform().also { it.position.set(0f, 0f, 10f) }

        val relative = RelativeTransform(transform, from, to)
        val actual = relative.transformationMatrix

        assertEquals(0f, actual.m30(), 0.0001f)
        assertEquals(0f, actual.m31(), 0.0001f)
        assertEquals(10f, actual.m32(), 0.0001f)
    }

    @Test
    fun `position reflects the translation of the relative matrix`() {
        val transform = SimpleTransform()
        transform.position.set(5f, 0f, 0f)
        val from = NullTransform
        val to = NullTransform

        val relative = RelativeTransform(transform, from, to)

        assertEquals(5f, relative.position.value.x(), 0.0001f)
        assertEquals(0f, relative.position.value.y(), 0.0001f)
        assertEquals(0f, relative.position.value.z(), 0.0001f)
    }
}
