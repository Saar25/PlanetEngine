package org.saar.lwjgl.util.buffer;

import org.joml.*;
import org.saar.lwjgl.util.DataWriter;

import java.nio.ByteBuffer;

public class BufferWriter implements DataWriter {

    private final ByteBuffer buffer;

    public BufferWriter(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void writeByte(byte value) {
        this.buffer.put(value);
    }

    @Override
    public void writeByte(int index, byte value) {
        this.buffer.put(index, value);
    }

    @Override
    public void writeShort(short value) {
        this.buffer.putShort(value);
    }

    @Override
    public void writeShort(int index, short value) {
        this.buffer.putShort(index, value);
    }

    @Override
    public void writeChar(char value) {
        this.buffer.putChar(value);
    }

    @Override
    public void writeChar(int index, char value) {
        this.buffer.putChar(index, value);
    }

    @Override
    public void writeInt(int value) {
        this.buffer.putInt(value);
    }

    @Override
    public void writeInt(int index, int value) {
        this.buffer.putInt(index, value);
    }

    @Override
    public void writeFloat(float value) {
        this.buffer.putFloat(value);
    }

    @Override
    public void writeFloat(int index, float value) {
        this.buffer.putFloat(index, value);
    }

    @Override
    public void writeLong(long value) {
        this.buffer.putLong(value);
    }

    @Override
    public void writeLong(int index, long value) {
        this.buffer.putLong(index, value);
    }

    @Override
    public void writeDouble(double value) {
        this.buffer.putDouble(value);
    }

    @Override
    public void writeDouble(int index, double value) {
        this.buffer.putDouble(index, value);
    }

    @Override
    public void write2f(Vector2fc value) {
        final int position = this.buffer.position() + 4 * 2;
        value.get(this.buffer).position(position);
    }

    @Override
    public void write2f(int index, Vector2fc value) {
        final int position = this.buffer.position() + 4 * 2;
        value.get(index, this.buffer).position(position);
    }

    @Override
    public void write3f(Vector3fc value) {
        final int position = this.buffer.position() + 4 * 3;
        value.get(this.buffer).position(position);
    }

    @Override
    public void write3f(int index, Vector3fc value) {
        final int position = this.buffer.position() + 4 * 3;
        value.get(index, this.buffer).position(position);
    }

    @Override
    public void write4f(Vector4fc value) {
        final int position = this.buffer.position() + 4 * 4;
        value.get(this.buffer).position(position);
    }

    @Override
    public void write4f(int index, Vector4fc value) {
        final int position = this.buffer.position() + 4 * 4;
        value.get(index, this.buffer).position(position);
    }

    @Override
    public void write4x4f(Matrix4fc value) {
        final int position = this.buffer.position() + 4 * 16;
        value.get(this.buffer).position(position);
    }

    @Override
    public void write4x4f(int index, Matrix4fc value) {
        final int position = this.buffer.position() + 4 * 16;
        value.get(index, this.buffer).position(position);
    }

    @Override
    public void write2i(Vector2ic value) {
        final int position = this.buffer.position() + 4 * 2;
        value.get(this.buffer).position(position);
    }

    @Override
    public void write2i(int index, Vector2ic value) {
        final int position = this.buffer.position() + 4 * 2;
        value.get(index, this.buffer).position(position);
    }

    @Override
    public void write3i(Vector3ic value) {
        final int position = this.buffer.position() + 4 * 3;
        value.get(this.buffer).position(position);
    }

    @Override
    public void write3i(int index, Vector3ic value) {
        final int position = this.buffer.position() + 4 * 3;
        value.get(index, this.buffer).position(position);
    }

    @Override
    public void write4i(Vector4ic value) {
        final int position = this.buffer.position() + 4 * 4;
        value.get(this.buffer).position(position);
    }

    @Override
    public void write4i(int index, Vector4ic value) {
        final int position = this.buffer.position() + 4 * 4;
        value.get(index, this.buffer).position(position);
    }
}
