package com.saar.lwjgl.opengl.constants;

import org.lwjgl.opengl.*;

public enum FormatType {

    DEPTH_COMPONENT(GL11.GL_DEPTH_COMPONENT),
    RGBA(GL11.GL_RGBA),
    RGB(GL11.GL_RGB),

    BGR(GL12.GL_BGR),
    BGRA(GL12.GL_BGRA),

    R3_G3_B2(GL11.GL_R3_G3_B2),
    RGB4(GL11.GL_RGB4),
    RGB5(GL11.GL_RGB5),
    RGB8(GL11.GL_RGB8),
    RGB10(GL11.GL_RGB10),
    RGB12(GL11.GL_RGB12),
    RGB16(GL11.GL_RGB16),
    RGBA2(GL11.GL_RGBA2),
    RGBA4(GL11.GL_RGBA4),
    RGB5_A1(GL11.GL_RGB5_A1),
    RGBA8(GL11.GL_RGBA8),
    RGB10_A2(GL11.GL_RGB10_A2),
    RGBA12(GL11.GL_RGBA12),
    RGBA16(GL11.GL_RGBA16),

    RGBA32F(GL30.GL_RGBA32F),
    RGBA16F(GL30.GL_RGBA16F),
    RGB32F(GL30.GL_RGB32F),
    RGB16F(GL30.GL_RGB16F),

    RED(GL11.GL_RED),
    GREEN(GL11.GL_GREEN),
    BLUE(GL11.GL_BLUE),
    ALPHA(GL11.GL_ALPHA),

    SRGB(GL21.GL_SRGB),
    SRGB8(GL21.GL_SRGB8),
    SRGB_ALPHA(GL21.GL_SRGB_ALPHA),
    SRGB8_ALPHA8(GL21.GL_SRGB8_ALPHA8),

    LUMINANCE(GL11.GL_LUMINANCE),
    LUMINANCE_ALPHA(GL11.GL_LUMINANCE_ALPHA),

    DEPTH_COMPONENT16(GL14.GL_DEPTH_COMPONENT16),
    DEPTH_COMPONENT24(GL14.GL_DEPTH_COMPONENT24),
    DEPTH_COMPONENT32(GL14.GL_DEPTH_COMPONENT32),
    ;

    private final int value;

    FormatType(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }
}
