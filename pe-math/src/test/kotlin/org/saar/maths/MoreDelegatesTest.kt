package org.saar.maths

import kotlin.test.Test
import kotlin.test.assertEquals

class MoreDelegatesTest {

    private class FloatHolder {
        var value: Float by MoreDelegates.clamp(0.5f)
    }

    private class IntHolder {
        var value: Int by MoreDelegates.clamp(5, 0, 10)
    }

    @Test
    fun `clamp float accepts a value inside the range`() {
        val holder = FloatHolder()

        holder.value = 0.8f

        assertEquals(0.8f, holder.value, 0f)
    }

    @Test
    fun `clamp float rejects a value above the range`() {
        val holder = FloatHolder()

        holder.value = 2f

        assertEquals(0.5f, holder.value, 0f)
    }

    @Test
    fun `clamp float rejects a value below the range`() {
        val holder = FloatHolder()

        holder.value = -1f

        assertEquals(0.5f, holder.value, 0f)
    }

    @Test
    fun `clamp int accepts a value inside the range`() {
        val holder = IntHolder()

        holder.value = 7

        assertEquals(7, holder.value)
    }

    @Test
    fun `clamp int rejects a value outside the range`() {
        val holder = IntHolder()

        holder.value = 100

        assertEquals(5, holder.value)
    }
}
