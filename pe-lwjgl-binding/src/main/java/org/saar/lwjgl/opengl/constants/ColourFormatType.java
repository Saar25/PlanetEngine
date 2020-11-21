package org.saar.lwjgl.opengl.constants;

public enum ColourFormatType {

    // 1 Component
    R8(InternalFormat.R8),
    R8I(InternalFormat.R8I),
    R8UI(InternalFormat.R8UI),

    R16(InternalFormat.R16),
    R16F(InternalFormat.R16F),
    R16I(InternalFormat.R16I),
    R16UI(InternalFormat.R16UI),

    R32F(InternalFormat.R32F),
    R32I(InternalFormat.R32I),
    R32UI(InternalFormat.R32UI),

    // 2 Components,
    RG8(InternalFormat.RG8),
    RG8I(InternalFormat.RG8I),
    RG8UI(InternalFormat.RG8UI),

    RG16(InternalFormat.RG16),
    RG16F(InternalFormat.RG16F),
    RG16I(InternalFormat.RG16I),
    RG16UI(InternalFormat.RG16UI),

    RG32F(InternalFormat.RG32F),
    RG32I(InternalFormat.RG32I),
    RG32UI(InternalFormat.RG32UI),

    // 3 Components
    R3_G3_B2(InternalFormat.R3_G3_B2),
    R11F_G11F_B10F(InternalFormat.R11F_G11F_B10F),

    RGB4(InternalFormat.RGB4),
    RGB5(InternalFormat.RGB5),
    RGB10(InternalFormat.RGB10),
    RGB12(InternalFormat.RGB12),

    RGB8(InternalFormat.RGB8),
    RGB8I(InternalFormat.RGB8I),
    RGB8UI(InternalFormat.RGB8UI),

    RGB16(InternalFormat.RGB16),
    RGB16F(InternalFormat.RGB16F),
    RGB16I(InternalFormat.RGB16I),
    RGB16UI(InternalFormat.RGB16UI),

    RGB32F(InternalFormat.RGB32F),
    RGB32I(InternalFormat.RGB32I),
    RGB32UI(InternalFormat.RGB32UI),

    // 4 Components
    RGB5_A1(InternalFormat.RGB5_A1),
    RGB10_A2(InternalFormat.RGB10_A2),

    RGBA2(InternalFormat.RGBA2),
    RGBA4(InternalFormat.RGBA4),
    RGBA12(InternalFormat.RGBA12),

    RGBA8(InternalFormat.RGBA8),
    RGBA8I(InternalFormat.RGBA8I),
    RGBA8UI(InternalFormat.RGBA8UI),

    RGBA16(InternalFormat.RGBA16),
    RGBA16F(InternalFormat.RGBA16F),
    RGBA16I(InternalFormat.RGBA16I),
    RGBA16UI(InternalFormat.RGBA16UI),

    RGBA32F(InternalFormat.RGBA32F),
    RGBA32I(InternalFormat.RGBA32I),
    RGBA32UI(InternalFormat.RGBA32UI),
    ;

    private final InternalFormat format;

    ColourFormatType(InternalFormat format) {
        this.format = format;
    }

    public InternalFormat get() {
        return this.format;
    }
}
