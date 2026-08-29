package org.saar.maths.noise

import org.joml.Vector2f
import kotlin.test.Test
import kotlin.test.assertEquals

class Noise2fTest {

    @Test
    fun `noise from a vector equals noise from its components`() {
        val v = Vector2f(1.5f, 2.5f)

        val actual = Noise2f.simplex.noise(v)

        val expected = Noise2f.simplex.noise(v.x, v.y)
        assertEquals(expected, actual, 0.0001f)
    }
}
