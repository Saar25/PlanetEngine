package org.saar.core.camera.projection

import org.joml.Anglef
import org.joml.Anglef.Companion.degrees
import org.joml.Matrix4f
import org.saar.core.camera.Projection
import org.saar.core.screen.MainScreen
import org.saar.core.screen.Screen
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Matrix4.ofProjection

class ScreenPerspectiveProjection(
    private val screen: Screen = MainScreen,
    override val fov: Anglef,
    override val near: Float,
    override val far: Float
) : PerspectiveProjection, Projection {

    constructor(fov: Float, near: Float, far: Float) : this(MainScreen, fov.degrees, near, far)

    private var lastWidth = -1f
    override val width: Float get() = this.screen.width.toFloat()

    private var lastHeight = -1f
    override val height: Float get() = this.screen.height.toFloat()

    override val matrix: Matrix4f = Matrix4.create()
        get() = if (width == lastWidth && height == lastHeight) field
        else {
            this.lastWidth = this.width
            this.lastHeight = this.height
            field.ofProjection(this.fov.radians, this.width, this.height, this.near, this.far)
        }
}
