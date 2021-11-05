package org.saar.lwjgl.util

import org.joml.*

class ByteListWriter(private val list: MutableList<Byte>) : DataWriter {

    override fun writeByte(value: Byte) {
        this.list.add(value)
    }

    override fun writeByte(index: Int, value: Byte) {
        this.list.add(index, value)
    }

    override fun writeShort(value: Short) {
        writeByte(value.toByte())
        writeByte((value.toInt() shr 8).toByte())
    }

    override fun writeShort(index: Int, value: Short) {
        writeByte(index, value.toByte())
        writeByte(index, (value.toInt() shr 8).toByte())
    }

    override fun writeChar(value: Char) {
        writeByte(value.code.toByte())
        writeByte((value.code shr 8).toByte())
    }

    override fun writeChar(index: Int, value: Char) {
        writeByte(index, value.code.toByte())
        writeByte(index, (value.code shr 8).toByte())
    }

    override fun writeInt(value: Int) {
        writeByte(value.toByte())
        writeByte((value shr 0x08).toByte())
        writeByte((value shr 0x0f).toByte())
        writeByte((value shr 0xf0).toByte())
    }

    override fun writeInt(index: Int, value: Int) {
        writeByte(index, value.toByte())
        writeByte(index, (value shr 0x08).toByte())
        writeByte(index, (value shr 0x0f).toByte())
        writeByte(index, (value shr 0xf0).toByte())
    }

    override fun writeFloat(value: Float) {
        writeInt(value.toBits())
    }

    override fun writeFloat(index: Int, value: Float) {
        writeInt(index, value.toBits())
    }

    override fun writeLong(value: Long) {
        writeInt(value.toInt())
        writeInt((value shr 32).toInt())
    }

    override fun writeLong(index: Int, value: Long) {
        writeInt(index, value.toInt())
        writeInt(index, (value shr 32).toInt())
    }

    override fun writeDouble(value: Double) {
        writeLong(value.toBits())
    }

    override fun writeDouble(index: Int, value: Double) {
        writeLong(index, value.toBits())
    }

    override fun write2f(value: Vector2fc) {
        writeFloat(value.x())
        writeFloat(value.y())
    }

    override fun write2f(index: Int, value: Vector2fc) {
        writeFloat(index, value.x())
        writeFloat(index, value.y())
    }

    override fun write3f(value: Vector3fc) {
        writeFloat(value.x())
        writeFloat(value.y())
        writeFloat(value.z())
    }

    override fun write3f(index: Int, value: Vector3fc) {
        writeFloat(index, value.x())
        writeFloat(index, value.y())
        writeFloat(index, value.z())
    }

    override fun write4f(value: Vector4fc) {
        writeFloat(value.x())
        writeFloat(value.y())
        writeFloat(value.z())
        writeFloat(value.w())
    }

    override fun write4f(index: Int, value: Vector4fc) {
        writeFloat(index, value.x())
        writeFloat(index, value.y())
        writeFloat(index, value.z())
        writeFloat(index, value.w())
    }

    override fun write4x4f(value: Matrix4fc) {
        writeFloat(value.m00())
        writeFloat(value.m01())
        writeFloat(value.m02())
        writeFloat(value.m03())

        writeFloat(value.m10())
        writeFloat(value.m11())
        writeFloat(value.m12())
        writeFloat(value.m13())

        writeFloat(value.m20())
        writeFloat(value.m21())
        writeFloat(value.m22())
        writeFloat(value.m23())

        writeFloat(value.m30())
        writeFloat(value.m31())
        writeFloat(value.m32())
        writeFloat(value.m33())
    }

    override fun write4x4f(index: Int, value: Matrix4fc) {
        writeFloat(index, value.m00())
        writeFloat(index, value.m01())
        writeFloat(index, value.m02())
        writeFloat(index, value.m03())

        writeFloat(index, value.m10())
        writeFloat(index, value.m11())
        writeFloat(index, value.m12())
        writeFloat(index, value.m13())

        writeFloat(index, value.m20())
        writeFloat(index, value.m21())
        writeFloat(index, value.m22())
        writeFloat(index, value.m23())

        writeFloat(index, value.m30())
        writeFloat(index, value.m31())
        writeFloat(index, value.m32())
        writeFloat(index, value.m33())
    }

    override fun write2i(value: Vector2ic) {
        writeInt(value.x())
        writeInt(value.y())
    }

    override fun write2i(index: Int, value: Vector2ic) {
        writeInt(index, value.x())
        writeInt(index, value.y())
    }

    override fun write3i(value: Vector3ic) {
        writeInt(value.x())
        writeInt(value.y())
        writeInt(value.z())
    }

    override fun write3i(index: Int, value: Vector3ic) {
        writeInt(index, value.x())
        writeInt(index, value.y())
        writeInt(index, value.z())
    }

    override fun write4i(value: Vector4ic) {
        writeInt(value.x())
        writeInt(value.y())
        writeInt(value.z())
        writeInt(value.w())
    }

    override fun write4i(index: Int, value: Vector4ic) {
        writeInt(index, value.x())
        writeInt(index, value.y())
        writeInt(index, value.z())
        writeInt(index, value.w())
    }
}