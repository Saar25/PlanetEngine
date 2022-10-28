package org.saar.lwjgl.opengl.polygonmode

import org.lwjgl.opengl.GL11
import org.saar.lwjgl.opengl.constants.Face

object PolygonMode {

    private val DEFAULTS = PolygonModeState(
        face = Face.FRONT_AND_BACK,
        mode = PolygonModeValue.FILL,
    )

    private var current: PolygonModeState = DEFAULTS

    @JvmStatic
    fun set(state: PolygonModeState) {
        if (state != this.current) {
            setPolygonMode(state)
        }

        this.current = state
    }

    @JvmStatic
    fun set(
        face: Face = this.current.face,
        mode: PolygonModeValue = this.current.mode,
    ) = set(PolygonModeState(face, mode))

    private fun setPolygonMode(state: PolygonModeState) = GL11.glPolygonMode(state.face.value, state.mode.value)
}