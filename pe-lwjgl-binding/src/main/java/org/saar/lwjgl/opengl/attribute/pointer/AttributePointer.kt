package org.saar.lwjgl.opengl.attribute.pointer

interface AttributePointer {
    fun point(index: Int, stride: Int, offset: Int)
    val bytesPerVertex: Int
}