package org.saar.core.mesh.lod

import kotlin.math.max
import kotlin.math.min

class ClampedInt(
    private val min: Int,
    private val max: Int
) {
    private var current = 0

    fun set(current: Int) {
        this.current = current
        checkRange()
    }

    fun inc() = set(this.current + 1)

    fun dec() = set(this.current - 1)

    fun get() = this.current

    private fun checkRange() {
        this.current = max(this.current, this.min)
        this.current = min(this.current, this.max)
    }
}
