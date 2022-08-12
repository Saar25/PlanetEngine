package org.saar.lwjgl.opengl.attribute

interface IAttribute {

    fun enable()

    fun disable()

    fun link(stride: Int, offset: Int)

    val bytesPerVertex: Int
}