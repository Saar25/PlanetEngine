package org.saar.maths

import org.joml.Vector4f
import org.joml.Vector4i
import org.joml.primitives.Rectanglef
import org.joml.primitives.Rectanglei

val Rectanglei.width: Int get() = lengthX()

val Rectanglei.height: Int get() = lengthY()

fun Rectanglei.offset(x: Int, y: Int, dest: Rectanglei = Rectanglei()): Rectanglei =
    dest.setMin(this.minX + x, this.minY + y).setMax(this.maxX + x, this.maxY + y)

fun Rectanglei.offset(x: Float, y: Float, dest: Rectanglef = Rectanglef()): Rectanglef =
    dest.setMin(this.minX + x, this.minY + y).setMax(this.maxX + x, this.maxY + y)

fun Rectanglei.toVector4i(dest: Vector4i = Vector4i()): Vector4i =
    dest.set(this.minX, this.minY, this.width, this.height)

fun Rectanglei.toVector4f(dest: Vector4f = Vector4f()): Vector4f =
    dest.set(this.minX.toFloat(), this.minY.toFloat(), this.width.toFloat(), this.height.toFloat())