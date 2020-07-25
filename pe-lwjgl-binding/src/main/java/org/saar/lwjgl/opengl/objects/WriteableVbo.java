package org.saar.lwjgl.opengl.objects;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface WriteableVbo extends IVbo {

    void allocateByte(long size);

    void allocateInt(long size);

    void allocateFloat(long size);

    void storeInt(long offset, int[] data);

    void storeFloat(long offset, float[] data);

    void storeByte(long offset, ByteBuffer data);

    void storeInt(long offset, IntBuffer data);

    void storeFloat(long offset, FloatBuffer data);


}
