package org.saar.lwjgl.util.buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public interface LwjglBuffer extends ReadonlyLwjglBuffer, AutoCloseable {

    Buffer position(int newPosition);

    Buffer limit(int newLimit);

    Buffer mark();

    Buffer reset();

    Buffer clear();

    Buffer flip();

    Buffer rewind();

    ByteBuffer put(byte b);

    ByteBuffer putChar(char value);

    ByteBuffer putShort(short value);

    ByteBuffer putInt(int value);

    ByteBuffer putLong(long value);

    ByteBuffer putFloat(float value);

    ByteBuffer putDouble(double value);

    ByteBuffer put(int index, byte b);

    ByteBuffer putChar(int index, char value);

    ByteBuffer putShort(int index, short value);

    ByteBuffer putInt(int index, int value);

    ByteBuffer putLong(int index, long value);

    ByteBuffer putFloat(int index, float value);

    ByteBuffer putDouble(int index, double value);

}
