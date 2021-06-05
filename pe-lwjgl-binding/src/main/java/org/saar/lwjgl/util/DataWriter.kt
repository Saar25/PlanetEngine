package org.saar.lwjgl.util

import org.joml.*

interface DataWriter {
    fun writeByte(value: Byte)

    fun writeShort(value: Short)
    fun writeChar(value: Char)

    fun writeInt(value: Int)
    fun writeFloat(value: Float)

    fun writeLong(value: Long)
    fun writeDouble(value: Double)

    fun write2f(value: Vector2fc)
    fun write3f(value: Vector3fc)
    fun write4f(value: Vector4fc)
    fun write4x4f(value: Matrix4fc)

    fun write2i(value: Vector2ic)
    fun write3i(value: Vector3ic)
    fun write4i(value: Vector4ic)
}