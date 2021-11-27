package org.saar.lwjgl.opengl.cullface

import org.lwjgl.opengl.GL11

enum class CullFaceValue(val value: Int) {

    FRONT(GL11.GL_FRONT),
    BACK(GL11.GL_BACK),
    FRONT_AND_BACK(GL11.GL_FRONT_AND_BACK),

}