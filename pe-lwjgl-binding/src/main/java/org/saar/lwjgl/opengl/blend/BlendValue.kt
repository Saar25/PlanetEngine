package org.saar.lwjgl.opengl.blend

import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL14

enum class BlendValue(val value: Int) {

    ZERO(GL11.GL_ZERO),
    ONE(GL11.GL_ONE),
    SRC_COLOR(GL11.GL_SRC_COLOR),
    ONE_MINUS_SRC_COLOR(GL11.GL_ONE_MINUS_SRC_COLOR),
    DST_COLOR(GL11.GL_DST_COLOR),
    ONE_MINUS_DST_COLOR(GL11.GL_ONE_MINUS_DST_COLOR),
    SRC_ALPHA(GL11.GL_SRC_ALPHA),
    ONE_MINUS_SRC_ALPHA(GL11.GL_ONE_MINUS_SRC_ALPHA),
    DST_ALPHA(GL11.GL_DST_ALPHA),
    ONE_MINUS_DST_ALPHA(GL11.GL_ONE_MINUS_DST_ALPHA),
    CONSTANT_COLOR(GL14.GL_CONSTANT_COLOR),
    ONE_MINUS_CONSTANT_COLOR(GL14.GL_ONE_MINUS_CONSTANT_COLOR),
    CONSTANT_ALPHA(GL14.GL_CONSTANT_ALPHA),
    ONE_MINUS_CONSTANT_ALPHA(GL14.GL_ONE_MINUS_CONSTANT_ALPHA),

}