package org.saar.maths.noise

import org.joml.Vector3f
import kotlin.test.Test
import kotlin.test.assertEquals

class Noise3fTest {

    @Test
    fun `noise from a vector equals noise from its components`() {
        val v = Vector3f(1.5f, 2.5f, 0.5f)

        val actual = Noise3f.simplex.noise(v)

        val expected = Noise3f.simplex.noise(v.x, v.y, v.z)
        assertEquals(expected, actual, 0.0001f)
    }
}
