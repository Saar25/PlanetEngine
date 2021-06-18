package org.saar.lwjgl.opengl.objects.attributes

interface IAttribute {

    fun enable()

    fun disable()

    fun link(stride: Int, offset: Int)

    val bytesPerVertex: Int
}