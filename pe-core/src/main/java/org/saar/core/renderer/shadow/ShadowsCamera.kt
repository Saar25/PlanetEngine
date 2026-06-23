package org.saar.core.renderer.shadow

import org.joml.Matrix4f
import org.saar.core.camera.ICamera
import org.saar.core.camera.projection.OrthographicProjection
import org.saar.core.light.IDirectionalLight
import org.saar.maths.transform.SimpleTransform
import org.saar.maths.utils.Matrix4
import org.saar.maths.utils.Matrix4.ofView
import org.saar.maths.utils.Vector3

class ShadowsCamera(
    override val projection: OrthographicProjection,
    private val light: IDirectionalLight
) : ICamera {

    override val viewMatrix: Matrix4f = Matrix4.create()
        get() = field.ofView(
            this.transform.position.value,
            this.transform.rotation.value,
        ).lookAlong(this.light.direction, Vector3.UP)

    override val transform: SimpleTransform = SimpleTransform()
}
