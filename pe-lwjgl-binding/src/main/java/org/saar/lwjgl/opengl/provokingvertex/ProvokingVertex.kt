package org.saar.lwjgl.opengl.provokingvertex

import org.lwjgl.opengl.GL32

object ProvokingVertex {

    private val DEFAULTS = ProvokingVertexValue.LAST

    private var current: ProvokingVertexValue = DEFAULTS

    @JvmStatic
    fun set(state: ProvokingVertexValue) {
        if (state != this.current) {
            setProvokingVertex(state)
        }

        this.current = state
    }

    @JvmStatic
    fun setFirst() = set(ProvokingVertexValue.FIRST)

    @JvmStatic
    fun setLast() = set(ProvokingVertexValue.LAST)

    private fun setProvokingVertex(state: ProvokingVertexValue) = GL32.glProvokingVertex(state.value)
}