package org.saar.maths.objects

import kotlin.test.Test
import kotlin.test.assertEquals

class DimensionsTest {

    @Test
    fun `data class exposes its components`() {
        val dimensions = Dimensions(1920, 1080)

        assertEquals(1920, dimensions.width)
        assertEquals(1080, dimensions.height)
    }

    @Test
    fun `two dimensions with the same components are equal`() {
        val a = Dimensions(10, 20)
        val b = Dimensions(10, 20)

        assertEquals(a, b)
    }
}
