package org.saar.gui.style.opacity

import kotlin.time.Duration

object OpacityValues {

    @JvmField
    val inherit: OpacityValue = OpacityValue { it.parent.style.opacity.opacity }

    @JvmStatic
    fun of(opacity: Float): OpacityValue = OpacityValue { opacity }

    @JvmStatic
    fun transition(from: Float, to: Float, time: Duration, start: Long = System.currentTimeMillis()) = OpacityValue {
        val delta = System.currentTimeMillis() - start
        val percent = delta / time.inWholeMilliseconds.toFloat()
        percent.coerceIn(0f, 1f) * (to - from) + from
    }
}