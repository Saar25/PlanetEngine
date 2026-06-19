package org.saar.core.camera.projection

import org.joml.Matrix4f
import org.saar.core.camera.Projection
import org.saar.core.screen.MainScreen
import org.saar.core.screen.Screen
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Matrix4.ofProjection

class ScreenPerspectiveProjection(
    private val screen: Screen = MainScreen,
    override var fov: Float,
    override var near: Float,
    override var far: Float
) : PerspectiveProjection, Projection {

    constructor(fov: Float, near: Float, far: Float) : this(MainScreen, fov, near, far)

    override val width: Float get() = this.screen.width.toFloat()

    override val height: Float get() = this.screen.height.toFloat()

    override val matrix: Matrix4f = Matrix4.create()
        get() = ofProjection(
            Math.toRadians(this.fov.toDouble()).toFloat(),
            this.width, this.height, this.near, this.far, field
        )
}
