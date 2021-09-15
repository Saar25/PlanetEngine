package org.saar.lwjgl.opengl.textures;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.InternalFormat;

import java.nio.ByteBuffer;

public interface WritableTexture extends ReadOnlyTexture {

    void allocate(TextureTarget target, int level, InternalFormat internalFormat, int width,
                  int height, int border, FormatType format, DataType type, ByteBuffer data);

    void allocate(TextureTarget target, int level, InternalFormat internalFormat, int width, int height, int border);

    void allocateMultisample(TextureTarget target, int samples, InternalFormat iFormat,
                             int width, int height, boolean fixedSampleLocations);

    void load(TextureTarget target, int level, int xOffset, int yOffset, int width,
              int height, FormatType format, DataType type, ByteBuffer data);

}
