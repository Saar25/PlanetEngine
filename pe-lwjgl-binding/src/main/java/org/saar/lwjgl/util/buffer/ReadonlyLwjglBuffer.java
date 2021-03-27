package org.saar.lwjgl.util.buffer;

import java.nio.ByteBuffer;

public interface ReadonlyLwjglBuffer extends AutoCloseable {

    ByteBuffer asByteBuffer();

    int capacity();

    int position();

    int limit();

    int remaining();

    boolean hasRemaining();

    byte get();

    char getChar();

    short getShort();

    int getInt();

    long getLong();

    float getFloat();

    double getDouble();

    byte get(int index);

    char getChar(int index);

    short getShort(int index);

    int getInt(int index);

    long getLong(int index);

    float getFloat(int index);

    double getDouble(int index);

}
