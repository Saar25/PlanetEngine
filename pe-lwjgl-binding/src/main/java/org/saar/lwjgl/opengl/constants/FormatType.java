package org.saar.lwjgl.opengl.constants;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;

public enum FormatType {

    DEPTH_COMPONENT(GL11.GL_DEPTH_COMPONENT),
    STENCIL_INDEX(GL11.GL_STENCIL_INDEX),
    DEPTH_STENCIL(GL30.GL_DEPTH_STENCIL),

    RED(GL11.GL_RED),
    RED_INTEGER(GL30.GL_RED_INTEGER),

    RG(GL30.GL_RG),
    RG_INTEGER(GL30.GL_RG_INTEGER),

    RGB(GL11.GL_RGB),
    RGB_INTEGER(GL30.GL_RGB_INTEGER),

    BGR(GL12.GL_BGR),
    BGR_INTEGER(GL30.GL_BGR_INTEGER),

    RGBA(GL11.GL_RGBA),
    RGBA_INTEGER(GL30.GL_RGBA_INTEGER),

    BGRA(GL12.GL_BGRA),
    BGRA_INTEGER(GL30.GL_BGRA_INTEGER),
    ;

    private final int value;

    FormatType(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }
}
