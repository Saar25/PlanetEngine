package org.saar.lwjgl.opengl.textures;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;

import java.nio.ByteBuffer;

public interface WritableTexture extends ReadOnlyTexture {

    void allocate(TextureTarget target, int level, FormatType internalFormat, int width,
                  int height, int border, FormatType format, DataType type, ByteBuffer data);

    void allocateMultisample(TextureTarget target, int samples, FormatType iFormat,
                                    int width, int height, boolean fixedSampleLocations);

    void load(TextureTarget target, int level, int xOffset, int yOffset, int width,
              int height, FormatType format, DataType type, ByteBuffer data);

}
