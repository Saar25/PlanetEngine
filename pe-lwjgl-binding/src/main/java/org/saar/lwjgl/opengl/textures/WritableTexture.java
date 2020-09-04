package org.saar.lwjgl.opengl.textures;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.IFormatType;

import java.nio.ByteBuffer;

public interface WritableTexture extends ReadOnlyTexture {

    void allocate(TextureTarget target, int level, IFormatType internalFormat, int width,
                  int height, int border, IFormatType format, DataType type, ByteBuffer data);

    void allocateMultisample(TextureTarget target, int samples, IFormatType iFormat,
                                    int width, int height, boolean fixedSampleLocations);

    void load(TextureTarget target, int level, int xOffset, int yOffset, int width,
              int height, IFormatType format, DataType type, ByteBuffer data);

}
