package org.saar.lwjgl.opengl.cullface

import org.lwjgl.opengl.GL11

enum class CullFaceOrder(val value: Int) {

    CLOCKWISE(GL11.GL_CW),
    COUNTER_CLOCKWISE(GL11.GL_CCW)
}