package org.saar.lwjgl.opengl.constants

import org.lwjgl.opengl.GL11

enum class Comparator(val value: Int) {

    NEVER(GL11.GL_NEVER),
    ALWAYS(GL11.GL_ALWAYS),

    EQUAL(GL11.GL_EQUAL),
    NOT_EQUAL(GL11.GL_NOTEQUAL),

    LESS(GL11.GL_LESS),
    LESS_EQUAL(GL11.GL_LEQUAL),

    GREATER(GL11.GL_GREATER),
    GREATER_EQUAL(GL11.GL_GEQUAL),

}