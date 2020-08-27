package org.saar.lwjgl.opengl.utils;

import java.nio.ByteBuffer;

public class BufferWriter {

    private final ByteBuffer buffer;

    public BufferWriter(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public void write(byte value) {
        this.buffer.put(value);
    }

    public void write(short value) {
        this.buffer.putShort(value);
    }

    public void write(char value) {
        this.buffer.putChar(value);
    }

    public void write(int value) {
        this.buffer.putInt(value);
    }

    public void write(float value) {
        this.buffer.putFloat(value);
    }

    public void write(double value) {
        this.buffer.putDouble(value);
    }

    public void write(long value) {
        this.buffer.putLong(value);
    }
}
