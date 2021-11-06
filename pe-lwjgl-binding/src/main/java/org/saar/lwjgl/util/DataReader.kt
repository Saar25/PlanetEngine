package org.saar.lwjgl.util

import org.joml.*

interface DataReader {

    fun readByte(index: Int): Byte
    fun readByte(): Byte

    fun readShort(index: Int): Short
    fun readShort(): Short

    fun readChar(index: Int): Char
    fun readChar(): Char


    fun readInt(index: Int): Int
    fun readInt(): Int

    fun readFloat(index: Int): Float
    fun readFloat(): Float

    fun readLong(index: Int): Long
    fun readLong(): Long

    fun readDouble(index: Int): Double
    fun readDouble(): Double

    fun read2f(index: Int): Vector2fc
    fun read2f(): Vector2fc

    fun read3f(index: Int): Vector3fc
    fun read3f(): Vector3fc

    fun read4f(index: Int): Vector4fc
    fun read4f(): Vector4fc

    fun read4x4f(index: Int): Matrix4fc
    fun read4x4f(): Matrix4fc

    fun read2i(index: Int): Vector2ic
    fun read2i(): Vector2ic

    fun read3i(index: Int): Vector3ic
    fun read3i(): Vector3ic

    fun read4i(index: Int): Vector4ic
    fun read4i(): Vector4ic
}