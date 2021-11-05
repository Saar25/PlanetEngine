package org.saar.lwjgl.util

import org.joml.*

interface DataWriter {

    fun writeByte(index: Int, value: Byte)
    fun writeByte(value: Byte)

    fun writeShort(index: Int, value: Short)
    fun writeShort(value: Short)

    fun writeChar(index: Int, value: Char)
    fun writeChar(value: Char)


    fun writeInt(index: Int, value: Int)
    fun writeInt(value: Int)

    fun writeFloat(index: Int, value: Float)
    fun writeFloat(value: Float)

    fun writeLong(index: Int, value: Long)
    fun writeLong(value: Long)

    fun writeDouble(index: Int, value: Double)
    fun writeDouble(value: Double)

    fun write2f(index: Int, value: Vector2fc)
    fun write2f(value: Vector2fc)

    fun write3f(index: Int, value: Vector3fc)
    fun write3f(value: Vector3fc)

    fun write4f(index: Int, value: Vector4fc)
    fun write4f(value: Vector4fc)

    fun write4x4f(index: Int, value: Matrix4fc)
    fun write4x4f(value: Matrix4fc)

    fun write2i(index: Int, value: Vector2ic)
    fun write2i(value: Vector2ic)

    fun write3i(index: Int, value: Vector3ic)
    fun write3i(value: Vector3ic)

    fun write4i(index: Int, value: Vector4ic)
    fun write4i(value: Vector4ic)
}