package org.saar.lwjgl.opengl.utils;

import org.joml.*;

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

    public void write(Vector2fc value) {
        value.get(this.buffer).position(this.buffer.position() + 2);
    }

    public void write(Vector3fc value) {
        value.get(this.buffer).position(this.buffer.position() + 3);
    }

    public void write(Vector4fc value) {
        value.get(this.buffer).position(this.buffer.position() + 4);
    }

    public void write(Matrix4fc value) {
        value.get(this.buffer).position(this.buffer.position() + 16);
    }

    public void write(Vector2ic value) {
        value.get(this.buffer).position(this.buffer.position() + 2);
    }

    public void write(Vector3ic value) {
        value.get(this.buffer).position(this.buffer.position() + 3);
    }

    public void write(Vector4ic value) {
        value.get(this.buffer).position(this.buffer.position() + 4);
    }
}
