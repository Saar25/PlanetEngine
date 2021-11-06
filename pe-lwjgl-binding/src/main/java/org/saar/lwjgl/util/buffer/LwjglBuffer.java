package org.saar.lwjgl.util.buffer;

public interface LwjglBuffer extends ReadonlyLwjglBuffer, AutoCloseable {

    LwjglBuffer position(int newPosition);

    LwjglBuffer limit(int newLimit);

    LwjglBuffer mark();

    LwjglBuffer reset();

    LwjglBuffer clear();

    LwjglBuffer flip();

    LwjglBuffer rewind();

    LwjglBuffer put(byte b);

    LwjglBuffer putChar(char value);

    LwjglBuffer putShort(short value);

    LwjglBuffer putInt(int value);

    LwjglBuffer putLong(long value);

    LwjglBuffer putFloat(float value);

    LwjglBuffer putDouble(double value);

    LwjglBuffer put(int index, byte b);

    LwjglBuffer putChar(int index, char value);

    LwjglBuffer putShort(int index, short value);

    LwjglBuffer putInt(int index, int value);

    LwjglBuffer putLong(int index, long value);

    LwjglBuffer putFloat(int index, float value);

    LwjglBuffer putDouble(int index, double value);

    LwjglBuffer put(byte[] b);

    LwjglBuffer put(byte[] b, int offset, int length);

    BufferReader getReader();

    BufferWriter getWriter();

    @Override
    void close();
}
