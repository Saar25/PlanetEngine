package org.saar.lwjgl.opengl.constants

import org.lwjgl.opengl.GL11

enum class Face(val value: Int) {

    FRONT(GL11.GL_FRONT),
    BACK(GL11.GL_BACK),
    FRONT_AND_BACK(GL11.GL_FRONT_AND_BACK),

}