package org.saar.lwjgl.opengl.constants;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public enum InternalFormat implements IInternalFormat {

    // 1 Component
    R8(1, GL30.GL_R8),
    R8I(1, GL30.GL_R8I),
    R8UI(1, GL30.GL_R8UI),

    R16(1, GL30.GL_R16),
    R16F(1, GL30.GL_R16F),
    R16I(1, GL30.GL_R16I),
    R16UI(1, GL30.GL_R16UI),

    R32F(1, GL30.GL_R32F),
    R32I(1, GL30.GL_R32I),
    R32UI(1, GL30.GL_R32UI),

    // 2 Components,
    RG8(2, GL30.GL_RG8),
    RG8I(2, GL30.GL_RG8I),
    RG8UI(2, GL30.GL_RG8UI),

    RG16(2, GL30.GL_RG16),
    RG16F(2, GL30.GL_RG16F),
    RG16I(2, GL30.GL_RG16I),
    RG16UI(2, GL30.GL_RG16UI),

    RG32F(2, GL30.GL_RG32F),
    RG32I(2, GL30.GL_RG32I),
    RG32UI(2, GL30.GL_RG8UI),

    // 3 Components
    R3_G3_B2(3, GL11.GL_R3_G3_B2),
    R11F_G11F_B10F(3, GL30.GL_R11F_G11F_B10F),

    RGB4(3, GL11.GL_RGB4),
    RGB5(3, GL11.GL_RGB5),
    RGB10(3, GL11.GL_RGB10),
    RGB12(3, GL11.GL_RGB12),

    RGB8(3, GL11.GL_RGB8),
    RGB8I(3, GL30.GL_RGB8I),
    RGB8UI(3, GL30.GL_RGB8UI),

    RGB16(3, GL11.GL_RGB16),
    RGB16F(3, GL30.GL_RGB16F),
    RGB16I(3, GL30.GL_RGB16I),
    RGB16UI(3, GL30.GL_RGB16UI),

    RGB32F(3, GL30.GL_RGB32F),
    RGB32I(3, GL30.GL_RGB32I),
    RGB32UI(3, GL30.GL_RGB32UI),

    // 4 Components
    RGB5_A1(4, GL11.GL_RGB5_A1),
    RGB10_A2(4, GL11.GL_RGB10_A2),

    RGBA2(4, GL11.GL_RGBA2),
    RGBA4(4, GL11.GL_RGBA4),
    RGBA12(4, GL11.GL_RGBA12),

    RGBA8(4, GL11.GL_RGBA8),
    RGBA8I(4, GL30.GL_RGBA8I),
    RGBA8UI(4, GL30.GL_RGBA8UI),

    RGBA16(4, GL11.GL_RGBA16),
    RGBA16F(4, GL30.GL_RGBA16F),
    RGBA16I(4, GL30.GL_RGBA16I),
    RGBA16UI(4, GL30.GL_RGBA16UI),

    RGBA32F(4, GL30.GL_RGBA32F),
    RGBA32I(4, GL30.GL_RGBA32I),
    RGBA32UI(4, GL30.GL_RGBA32UI),
    ;

    private final int components;
    private final int value;

    InternalFormat(int components, int value) {
        this.components = components;
        this.value = value;
    }

    public int get() {
        return this.value;
    }

    public int getComponents() {
        return this.components;
    }
}
