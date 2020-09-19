package org.saar.lwjgl.opengl.objects.pbos;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.IFormatType;

import java.nio.ByteBuffer;

public interface ReadablePbo {

    void readPixels(int x, int y, int width, int height, IFormatType format,
                    DataType dataType, ByteBuffer pixels);

    void delete();

}
