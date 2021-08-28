package org.saar.lwjgl.opengl.stencil

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL14

enum class StencilValue(val value: Int) {

    KEEP(GL11.GL_KEEP),
    REPLACE(GL11.GL_REPLACE),
    INVERT(GL11.GL_INVERT),
    ZERO(GL11.GL_ZERO),

    INCREMENT(GL11.GL_INCR),
    INCREMENT_WRAP(GL14.GL_INCR_WRAP),

    DECREMENT(GL11.GL_DECR),
    DECREMENT_WRAP(GL14.GL_DECR_WRAP),

}