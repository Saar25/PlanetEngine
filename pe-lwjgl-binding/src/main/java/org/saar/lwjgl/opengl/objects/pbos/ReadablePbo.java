package org.saar.lwjgl.opengl.objects.pbos;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.InternalFormat;

public interface ReadablePbo {

    void readPixels(int x, int y, int width, int height, InternalFormat format, DataType dataType);

    void delete();

}
