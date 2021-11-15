package org.saar.lwjgl.util.buffer;

import org.joml.*;
import org.saar.lwjgl.util.DataReader;

import java.nio.ByteBuffer;

public class BufferReader implements DataReader {

    private final ByteBuffer buffer;

    public BufferReader(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public byte readByte() {
        return this.buffer.get();
    }

    @Override
    public byte readByte(int index) {
        return this.buffer.get(index);
    }

    @Override
    public short readShort() {
        return this.buffer.getShort();
    }

    @Override
    public short readShort(int index) {
        return this.buffer.getShort(index);
    }

    @Override
    public char readChar() {
        return this.buffer.getChar();
    }

    @Override
    public char readChar(int index) {
        return this.buffer.getChar(index);
    }

    @Override
    public int readInt() {
        return this.buffer.getInt();
    }

    @Override
    public int readInt(int index) {
        return this.buffer.getInt(index);
    }

    @Override
    public float readFloat() {
        return this.buffer.getFloat();
    }

    @Override
    public float readFloat(int index) {
        return this.buffer.getFloat(index);
    }

    @Override
    public long readLong() {
        return this.buffer.getLong();
    }

    @Override
    public long readLong(int index) {
        return this.buffer.getLong(index);
    }

    @Override
    public double readDouble() {
        return this.buffer.getDouble();
    }

    @Override
    public double readDouble(int index) {
        return this.buffer.getDouble(index);
    }

    @Override
    public Vector2fc read2f() {
        final int position = this.buffer.position();
        this.buffer.position(position + 4 * 2);
        return new Vector2f(position, this.buffer);
    }

    @Override
    public Vector2fc read2f(int index) {
        return new Vector2f(index, this.buffer);
    }

    @Override
    public Vector3fc read3f() {
        final int position = this.buffer.position();
        this.buffer.position(position + 4 * 3);
        return new Vector3f(position, this.buffer);
    }

    @Override
    public Vector3fc read3f(int index) {
        return new Vector3f(index, this.buffer);
    }

    @Override
    public Vector4fc read4f() {
        final int position = this.buffer.position();
        this.buffer.position(position + 4 * 4);
        return new Vector4f(position, this.buffer);
    }

    @Override
    public Vector4fc read4f(int index) {
        return new Vector4f(index, this.buffer);
    }

    @Override
    public Matrix4fc read4x4f() {
        return new Matrix4f(
                read4f(), read4f(),
                read4f(), read4f()
        );
    }

    @Override
    public Matrix4fc read4x4f(int index) {
        return new Matrix4f(
                read4f(index), read4f(index),
                read4f(index), read4f(index)
        );
    }

    @Override
    public Vector2ic read2i() {
        final int position = this.buffer.position();
        this.buffer.position(position + 4 * 2);
        return new Vector2i(position, this.buffer);
    }

    @Override
    public Vector2ic read2i(int index) {
        return new Vector2i(index, this.buffer);
    }

    @Override
    public Vector3ic read3i() {
        final int position = this.buffer.position();
        this.buffer.position(position + 4 * 3);
        return new Vector3i(position, this.buffer);
    }

    @Override
    public Vector3ic read3i(int index) {
        return new Vector3i(index, this.buffer);
    }

    @Override
    public Vector4ic read4i() {
        final int position = this.buffer.position();
        this.buffer.position(position + 4 * 4);
        return new Vector4i(position, this.buffer);
    }

    @Override
    public Vector4ic read4i(int index) {
        return new Vector4i(index, this.buffer);
    }
}
