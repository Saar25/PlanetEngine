package org.saar.lwjgl.opengl.constants;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

public enum InternalFormat {

    // 1 Component
    R8(GL30.GL_R8, FormatType.RED, DataType.U_BYTE),
    R8I(GL30.GL_R8I, FormatType.RED, DataType.INT),
    R8UI(GL30.GL_R8UI, FormatType.RED, DataType.U_INT),

    R16(GL30.GL_R16, FormatType.RED, DataType.U_BYTE),
    R16F(GL30.GL_R16F, FormatType.RED, DataType.FLOAT),
    R16I(GL30.GL_R16I, FormatType.RED, DataType.INT),
    R16UI(GL30.GL_R16UI, FormatType.RED, DataType.U_INT),

    R32F(GL30.GL_R32F, FormatType.RED, DataType.U_BYTE),
    R32I(GL30.GL_R32I, FormatType.RED, DataType.INT),
    R32UI(GL30.GL_R32UI, FormatType.RED, DataType.U_INT),

    // 2 Components,
    RG8(GL30.GL_RG8, FormatType.RG, DataType.U_BYTE),
    RG8I(GL30.GL_RG8I, FormatType.RG, DataType.INT),
    RG8UI(GL30.GL_RG8UI, FormatType.RG, DataType.U_INT),

    RG16(GL30.GL_RG16, FormatType.RG, DataType.U_BYTE),
    RG16F(GL30.GL_RG16F, FormatType.RG, DataType.FLOAT),
    RG16I(GL30.GL_RG16I, FormatType.RG, DataType.INT),
    RG16UI(GL30.GL_RG16UI, FormatType.RG, DataType.U_INT),

    RG32F(GL30.GL_RG32F, FormatType.RG, DataType.FLOAT),
    RG32I(GL30.GL_RG32I, FormatType.RG, DataType.INT),
    RG32UI(GL30.GL_RG8UI, FormatType.RG, DataType.U_INT),

    // 3 Components
    R3_G3_B2(GL11.GL_R3_G3_B2, FormatType.RGB, DataType.U_BYTE_3_3_2),
    R11F_G11F_B10F(GL30.GL_R11F_G11F_B10F, FormatType.RGB, DataType.U_INT_10F_11F_11F_REV),

    RGB4(GL11.GL_RGB4, FormatType.RGB, DataType.U_BYTE),
    RGB5(GL11.GL_RGB5, FormatType.RGB, DataType.U_BYTE),
    RGB10(GL11.GL_RGB10, FormatType.RGB, DataType.U_BYTE),
    RGB12(GL11.GL_RGB12, FormatType.RGB, DataType.U_BYTE),

    RGB8(GL11.GL_RGB8, FormatType.RGB, DataType.U_BYTE),
    RGB8I(GL30.GL_RGB8I, FormatType.RGB, DataType.INT),
    RGB8UI(GL30.GL_RGB8UI, FormatType.RGB, DataType.U_INT),

    RGB16(GL11.GL_RGB16, FormatType.RGB, DataType.U_BYTE),
    RGB16F(GL30.GL_RGB16F, FormatType.RGB, DataType.FLOAT),
    RGB16I(GL30.GL_RGB16I, FormatType.RGB, DataType.INT),
    RGB16UI(GL30.GL_RGB16UI, FormatType.RGB, DataType.U_INT),

    RGB32F(GL30.GL_RGB32F, FormatType.RGB, DataType.FLOAT),
    RGB32I(GL30.GL_RGB32I, FormatType.RGB, DataType.INT),
    RGB32UI(GL30.GL_RGB32UI, FormatType.RGB, DataType.U_INT),

    // 4 Components
    RGB5_A1(GL11.GL_RGB5_A1, FormatType.RGBA, DataType.U_SHORT_5_5_5_1),
    RGB10_A2(GL11.GL_RGB10_A2, FormatType.RGBA, DataType.U_INT_10_10_10_2),

    RGBA2(GL11.GL_RGBA2, FormatType.RGBA, DataType.U_BYTE),
    RGBA4(GL11.GL_RGBA4, FormatType.RGBA, DataType.U_BYTE),
    RGBA12(GL11.GL_RGBA12, FormatType.RGBA, DataType.U_BYTE),

    RGBA8(GL11.GL_RGBA8, FormatType.RGBA, DataType.U_BYTE),
    RGBA8I(GL30.GL_RGBA8I, FormatType.RGBA, DataType.INT),
    RGBA8UI(GL30.GL_RGBA8UI, FormatType.RGBA, DataType.U_INT),

    RGBA16(GL11.GL_RGBA16, FormatType.RGBA, DataType.U_BYTE),
    RGBA16F(GL30.GL_RGBA16F, FormatType.RGBA, DataType.FLOAT),
    RGBA16I(GL30.GL_RGBA16I, FormatType.RGBA, DataType.INT),
    RGBA16UI(GL30.GL_RGBA16UI, FormatType.RGBA, DataType.U_INT),

    RGBA32F(GL30.GL_RGBA32F, FormatType.RGBA, DataType.FLOAT),
    RGBA32I(GL30.GL_RGBA32I, FormatType.RGBA, DataType.INT),
    RGBA32UI(GL30.GL_RGBA32UI, FormatType.RGBA, DataType.U_INT),

    // Depth
    DEPTH16(GL14.GL_DEPTH_COMPONENT16, FormatType.DEPTH_COMPONENT, DataType.U_BYTE),
    DEPTH24(GL14.GL_DEPTH_COMPONENT24, FormatType.DEPTH_COMPONENT, DataType.U_BYTE),
    DEPTH32(GL14.GL_DEPTH_COMPONENT32, FormatType.DEPTH_COMPONENT, DataType.U_BYTE),

    // Stencil
    STENCIL_INDEX8(GL30.GL_STENCIL_INDEX8, FormatType.STENCIL_INDEX, DataType.U_BYTE),

    // Depth stencil
    DEPTH24_STENCIL8(GL30.GL_DEPTH24_STENCIL8, FormatType.DEPTH_STENCIL, DataType.U_INT_24_8),
    DEPTH32F_STENCIL8(GL30.GL_DEPTH32F_STENCIL8, FormatType.DEPTH_STENCIL, DataType.FLOAT),
    ;

    private final int value;
    private final FormatType formatType;
    private final DataType dataType;

    InternalFormat(int value, FormatType formatType, DataType dataType) {
        this.value = value;
        this.formatType = formatType;
        this.dataType = dataType;
    }

    public int get() {
        return this.value;
    }

    public FormatType getFormatType() {
        return this.formatType;
    }

    public DataType getDataType() {
        return this.dataType;
    }
}
