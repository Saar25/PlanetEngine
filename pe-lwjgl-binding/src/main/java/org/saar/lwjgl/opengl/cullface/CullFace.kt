package org.saar.lwjgl.opengl.cullface

import org.lwjgl.opengl.GL11

object CullFace {

    private val DEFAULTS = CullFaceState(
        enabled = false,
        face = CullFaceValue.BACK,
        order = CullFaceOrder.COUNTER_CLOCKWISE,
    )

    private const val target = GL11.GL_CULL_FACE

    private var current: CullFaceState = DEFAULTS

    @JvmStatic
    fun enable() = set(enabled = true)

    @JvmStatic
    fun disable() = set(enabled = false)

    @JvmStatic
    fun set(state: CullFaceState) {
        if (state.enabled != this.current.enabled) {
            setEnabled(state.enabled)
        }
        if (state.face != this.current.face) {
            setFace(state.face)
        }
        if (state.order != this.current.order) {
            setOrder(state.order)
        }

        this.current = state
    }

    @JvmStatic
    fun set(
        enabled: Boolean = current.enabled,
        face: CullFaceValue = current.face,
        order: CullFaceOrder = current.order,
    ) = set(CullFaceState(enabled, face, order))

    private fun setEnabled(enabled: Boolean) = if (enabled) GL11.glEnable(this.target) else GL11.glDisable(this.target)

    private fun setFace(face: CullFaceValue) = GL11.glCullFace(face.value)

    private fun setOrder(order: CullFaceOrder) = GL11.glFrontFace(order.value)
}