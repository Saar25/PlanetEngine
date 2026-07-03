package org.saar.core.screen

import org.saar.core.screen.Screens.toScreen
import org.saar.lwjgl.opengl.fbo.Fbo
import org.saar.lwjgl.opengl.utils.GlBuffer

@Deprecated("This class makes code not readable")
class ScreenSwap<T : ScreenPrototype>(
    private val screenPrototypeA: T,
    private val screenPrototypeB: T
) {

    private val screenA: OffScreen = this.screenPrototypeA.toScreen(Fbo.create(), 0, 0)
    private val screenB: OffScreen = this.screenPrototypeB.toScreen(Fbo.create(), 0, 0)

    private val swapMap = mapOf(
        this.screenA to this.screenB,
        this.screenB to this.screenA,
    )

    private val prototypeMap = mapOf(
        this.screenA to this.screenPrototypeA,
        this.screenB to this.screenPrototypeB,
    )

    var current = this.screenA
        private set

    val prototype get() = this.prototypeMap[this.current]!!

    fun swap(): OffScreen {
        this.current = this.swapMap[this.current]!!
        return this.current
    }

    fun clearAll(buffers: Iterable<GlBuffer>) {
        this.screenA.clear(buffers)
        this.screenB.clear(buffers)
    }

    fun clearAll(vararg buffers: GlBuffer) = clearAll(buffers.asIterable())

    fun assureSize(width: Int, height: Int) {
        this.screenA.assureSize(width, height)
        this.screenB.assureSize(width, height)
    }

    fun delete() {
        this.screenA.delete()
        this.screenB.delete()
    }
}