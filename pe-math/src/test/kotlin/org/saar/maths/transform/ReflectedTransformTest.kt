package org.saar.maths.transform

import org.joml.primitives.Planef
import org.saar.maths.utils.Vector3
import kotlin.test.Test
import kotlin.test.assertEquals

class ReflectedTransformTest {

    @Test
    fun `position is reflected across the plane`() {
        val source = SimpleTransform()
        source.position.set(1f, 2f, 3f)
        val plane = Planef(0f, 0f, 1f, 0f)

        val reflected = ReflectedTransform(source, plane)

        assertEquals(1f, reflected.position.value.x(), 0.0001f)
        assertEquals(2f, reflected.position.value.y(), 0.0001f)
        assertEquals(-3f, reflected.position.value.z(), 0.0001f)
    }

    @Test
    fun `scale is passed through unchanged`() {
        val source = SimpleTransform()
        source.scale.set(2f, 3f, 4f)
        val plane = Planef(0f, 0f, 1f, 0f)

        val reflected = ReflectedTransform(source, plane)

        assertEquals(2f, reflected.scale.value.x(), 0f)
        assertEquals(3f, reflected.scale.value.y(), 0f)
        assertEquals(4f, reflected.scale.value.z(), 0f)
    }

    @Test
    fun `an identity source produces an identity transformation matrix`() {
        val source = SimpleTransform()
        val plane = Planef(0f, 0f, 1f, 0f)

        val reflected = ReflectedTransform(source, plane)

        assertEquals(1f, reflected.transformationMatrix.m00(), 0.0001f)
        assertEquals(1f, reflected.transformationMatrix.m22(), 0.0001f)
    }
}
