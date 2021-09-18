package org.saar.lwjgl.opengl.texture;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;

import java.nio.ByteBuffer;

public interface WritableTexture2D extends MutableTexture {

    void load(int level, int xOffset, int yOffset, int width,
              int height, FormatType format, DataType type, ByteBuffer data);

    default void load(int level, FormatType format, DataType type, ByteBuffer data) {
        load(level, 0, 0, getWidth(), getHeight(), format, type, data);
    }

    int getWidth();

    int getHeight();

}
