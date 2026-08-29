package org.saar.maths.noise

import kotlin.test.Test
import kotlin.test.assertEquals

class MultipliedNoise3fTest {

    @Test
    fun `noise multiplies the underlying value by the multiplier`() {
        val noise = MultipliedNoise3f({ _, _, z -> z }, 3f)

        val actual = noise.noise(1f, 2f, 4f)

        val expected = 12f
        assertEquals(expected, actual, 0.0001f)
    }
}
