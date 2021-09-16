package org.saar.lwjgl.opengl.texture;

import org.saar.lwjgl.opengl.constants.InternalFormat;

public interface MutableTexture extends WritableTexture {

    default void allocate(int level, InternalFormat internalFormat, int width, int height) {
        allocate(level, internalFormat, width, height, 0);
    }

    void allocate(int level, InternalFormat internalFormat, int width, int height, int border);

    void allocateMultisample(int samples, InternalFormat iFormat, int width, int height, boolean fixedSampleLocations);

}
