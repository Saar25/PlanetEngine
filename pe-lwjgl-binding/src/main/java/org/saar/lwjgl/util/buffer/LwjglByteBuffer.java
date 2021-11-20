package org.saar.lwjgl.util.buffer;

import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

public class LwjglByteBuffer implements LwjglBuffer {

    private final ByteBuffer buffer;

    public LwjglByteBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public static LwjglByteBuffer wrap(ByteBuffer buffer) {
        return new LwjglByteBuffer(buffer);
    }

    public static LwjglByteBuffer allocate(int capacity) {
        final ByteBuffer buffer = MemoryUtil.memAlloc(capacity);
        return new LwjglByteBuffer(buffer);
    }

    public static LwjglByteBuffer callocate(int capacity) {
        final ByteBuffer buffer = MemoryUtil.memCalloc(capacity);
        return new LwjglByteBuffer(buffer);
    }

    @Override
    public ByteBuffer asByteBuffer() {
        return this.buffer;
    }

    @Override
    public LwjglByteBuffer position(int newPosition) {
        this.buffer.position(newPosition);
        return this;
    }

    @Override
    public LwjglByteBuffer limit(int newLimit) {
        this.buffer.limit(newLimit);
        return this;
    }

    @Override
    public LwjglByteBuffer mark() {
        this.buffer.mark();
        return this;
    }

    @Override
    public LwjglByteBuffer reset() {
        this.buffer.reset();
        return this;
    }

    @Override
    public LwjglByteBuffer clear() {
        this.buffer.clear();
        return this;
    }

    @Override
    public LwjglByteBuffer flip() {
        this.buffer.flip();
        return this;
    }

    @Override
    public LwjglByteBuffer rewind() {
        this.buffer.rewind();
        return this;
    }

    @Override
    public LwjglByteBuffer put(byte b) {
        this.buffer.put(b);
        return this;
    }

    @Override
    public LwjglByteBuffer putChar(char value) {
        this.buffer.putChar(value);
        return this;
    }

    @Override
    public LwjglByteBuffer putShort(short value) {
        this.buffer.putShort(value);
        return this;
    }

    @Override
    public LwjglByteBuffer putInt(int value) {
        this.buffer.putInt(value);
        return this;
    }

    @Override
    public LwjglByteBuffer putLong(long value) {
        this.buffer.putLong(value);
        return this;
    }

    @Override
    public LwjglByteBuffer putFloat(float value) {
        this.buffer.putFloat(value);
        return this;
    }

    @Override
    public LwjglByteBuffer putDouble(double value) {
        this.buffer.putDouble(value);
        return this;
    }

    @Override
    public LwjglByteBuffer put(int index, byte b) {
        this.buffer.put(index, b);
        return this;
    }

    @Override
    public LwjglByteBuffer putChar(int index, char value) {
        this.buffer.putChar(index, value);
        return this;
    }

    @Override
    public LwjglByteBuffer putShort(int index, short value) {
        this.buffer.putShort(index, value);
        return this;
    }

    @Override
    public LwjglByteBuffer putInt(int index, int value) {
        this.buffer.putInt(index, value);
        return this;
    }

    @Override
    public LwjglByteBuffer putLong(int index, long value) {
        this.buffer.putLong(index, value);
        return this;
    }

    @Override
    public LwjglByteBuffer putFloat(int index, float value) {
        this.buffer.putFloat(index, value);
        return this;
    }

    @Override
    public LwjglByteBuffer putDouble(int index, double value) {
        this.buffer.putDouble(index, value);
        return this;
    }

    @Override
    public LwjglByteBuffer put(byte[] b) {
        this.buffer.put(b);
        return this;
    }

    @Override
    public LwjglByteBuffer put(byte[] b, int offset, int length) {
        this.buffer.put(b, offset, length);
        return this;
    }

    @Override
    public BufferReader getReader() {
        return new BufferReader(this.buffer);
    }

    @Override
    public BufferWriter getWriter() {
        return new BufferWriter(this.buffer);
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
