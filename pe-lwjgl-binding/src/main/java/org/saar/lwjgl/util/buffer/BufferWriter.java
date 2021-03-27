package org.saar.lwjgl.util.buffer;

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
        final int position = this.buffer.position() + 4 * 2;
        value.get(this.buffer).position(position);
    }

    public void write(Vector3fc value) {
        final int position = this.buffer.position() + 4 * 3;
        value.get(this.buffer).position(position);
    }

    public void write(Vector4fc value) {
        final int position = this.buffer.position() + 4 * 4;
        value.get(this.buffer).position(position);
    }

    public void write(Matrix4fc value) {
        final int position = this.buffer.position() + 4 * 16;
        value.get(this.buffer).position(position);
    }

    public void write(Vector2ic value) {
        final int position = this.buffer.position() + 4 * 2;
        value.get(this.buffer).position(position);
    }

    public void write(Vector3ic value) {
        final int position = this.buffer.position() + 4 * 3;
        value.get(this.buffer).position(position);
    }

    public void write(Vector4ic value) {
        final int position = this.buffer.position() + 4 * 4;
        value.get(this.buffer).position(position);
    }
}
