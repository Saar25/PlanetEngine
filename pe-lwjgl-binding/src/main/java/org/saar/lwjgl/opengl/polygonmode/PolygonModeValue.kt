package org.saar.lwjgl.opengl.polygonmode

import org.lwjgl.opengl.GL11

enum class PolygonModeValue(val value: Int) {

    POINT(GL11.GL_POINT),
    LINE(GL11.GL_LINE),
    FILL(GL11.GL_FILL),

}