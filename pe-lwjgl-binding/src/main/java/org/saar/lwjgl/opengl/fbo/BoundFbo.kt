package org.saar.lwjgl.opengl.fbo

import java.util.*

object BoundFbo {

    private val bound: MutableMap<FboTarget, Int> = EnumMap(FboTarget::class.java)

    @JvmStatic
    fun isBound(target: FboTarget, id: Int) = this.get(target) == id

    @JvmStatic
    fun set(target: FboTarget, id: Int) {
        this.bound[target] = id
        if (target == FboTarget.FRAMEBUFFER) {
            this.bound[FboTarget.READ_FRAMEBUFFER] = id
            this.bound[FboTarget.DRAW_FRAMEBUFFER] = id
        }
    }

    fun get(target: FboTarget) = this.bound.getOrDefault(target, 0)
}
