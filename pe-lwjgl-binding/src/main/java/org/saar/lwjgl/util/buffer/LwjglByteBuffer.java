package org.saar.lwjgl.util.buffer;

import org.lwjgl.system.MemoryUtil;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class LwjglByteBuffer implements LwjglBuffer {

    private final ByteBuffer buffer;

    public LwjglByteBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public ByteBuffer asByteBuffer() {
        return this.buffer;
    }

    @Override
    public Buffer position(int newPosition) {
        return this.buffer.position(newPosition);
    }

    @Override
    public Buffer limit(int newLimit) {
        return this.buffer.limit(newLimit);
    }

    @Override
    public Buffer mark() {
        return this.buffer.mark();
    }

    @Override
    public Buffer reset() {
        return this.buffer.reset();
    }

    @Override
    public Buffer clear() {
        return this.buffer.clear();
    }

    @Override
    public Buffer flip() {
        return this.buffer.flip();
    }

    @Override
    public Buffer rewind() {
        return this.buffer.rewind();
    }

    @Override
    public ByteBuffer put(byte b) {
        return this.buffer.put(b);
    }

    @Override
    public ByteBuffer putChar(char value) {
        return this.buffer.putChar(value);
    }

    @Override
    public ByteBuffer putShort(short value) {
        return this.buffer.putShort(value);
    }

    @Override
    public ByteBuffer putInt(int value) {
        return this.buffer.putInt(value);
    }

    @Override
    public ByteBuffer putLong(long value) {
        return this.buffer.putLong(value);
    }

    @Override
    public ByteBuffer putFloat(float value) {
        return this.buffer.putFloat(value);
    }

    @Override
    public ByteBuffer putDouble(double value) {
        return this.buffer.putDouble(value);
    }

    @Override
    public ByteBuffer put(int index, byte b) {
        return this.buffer.put(index, b);
    }

    @Override
    public ByteBuffer putChar(int index, char value) {
        return this.buffer.putChar(index, value);
    }

    @Override
    public ByteBuffer putShort(int index, short value) {
        return this.buffer.putShort(index, value);
    }

    @Override
    public ByteBuffer putInt(int index, int value) {
        return this.buffer.putInt(index, value);
    }

    @Override
    public ByteBuffer putLong(int index, long value) {
        return this.buffer.putLong(index, value);
    }

    @Override
    public ByteBuffer putFloat(int index, float value) {
        return this.buffer.putFloat(index, value);
    }

    @Override
    public ByteBuffer putDouble(int index, double value) {
        return this.buffer.putDouble(index, value);
    }

    @Override
    public int capacity() {
        return this.buffer.capacity();
    }

    @Override
    public int position() {
        return this.buffer.position();
    }

    @Override
    public int limit() {
        return this.buffer.limit();
    }

    @Override
    public int remaining() {
        return this.buffer.remaining();
    }

    @Override
    public boolean hasRemaining() {
        return this.buffer.hasRemaining();
    }

    @Override
    public byte get() {
        return this.buffer.get();
    }

    @Override
    public char getChar() {
        return this.buffer.getChar();
    }

    @Override
    public short getShort() {
        return this.buffer.getShort();
    }

    @Override
    public int getInt() {
        return this.buffer.getInt();
    }

    @Override
    public long getLong() {
        return this.buffer.getLong();
    }

    @Override
    public float getFloat() {
        return this.buffer.getFloat();
    }

    @Override
    public double getDouble() {
        return this.buffer.getDouble();
    }

    @Override
    public byte get(int index) {
        return this.buffer.get(index);
    }

    @Override
    public char getChar(int index) {
        return this.buffer.getChar(index);
    }

    @Override
    public short getShort(int index) {
        return this.buffer.getShort(index);
    }

    @Override
    public int getInt(int index) {
        return this.buffer.getInt(index);
    }

    @Override
    public long getLong(int index) {
        return this.buffer.getLong(index);
    }

    @Override
    public float getFloat(int index) {
        return this.buffer.getFloat(index);
    }

    @Override
    public double getDouble(int index) {
        return this.buffer.getDouble(index);
    }

    @Override
    public void close() {
        MemoryUtil.memFree(this.buffer);
    }
}
