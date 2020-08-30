package org.saar.lwjgl.opengl.objects;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public interface WriteableVbo extends IVbo {

    void allocateByte(long size);

    void allocateInt(long size);

    void allocateFloat(long size);

    void storeData(long offset, int[] data);

    void storeData(long offset, float[] data);

    void storeData(long offset, ByteBuffer data);

    void storeData(long offset, IntBuffer data);

    void storeData(long offset, FloatBuffer data);


}
