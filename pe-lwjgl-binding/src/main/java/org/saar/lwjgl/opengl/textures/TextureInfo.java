package org.saar.lwjgl.opengl.textures;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;

import java.nio.ByteBuffer;

public class TextureInfo {

    private final int width;
    private final int height;
    private final FormatType formatType;
    private final DataType dataType;
    private final ByteBuffer data;

    public TextureInfo(int width, int height, FormatType formatType, DataType dataType, ByteBuffer data) {
        this.width = width;
        this.height = height;
        this.formatType = formatType;
        this.dataType = dataType;
        this.data = data;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public FormatType getFormatType() {
        return this.formatType;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public ByteBuffer getData() {
        return this.data;
    }
}
