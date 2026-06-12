package org.saar.core.camera

import org.saar.maths.transform.ReadonlyTransform
import org.saar.maths.transform.SimpleTransform
import org.saar.maths.utils.Matrix4

class ReadonlyCamera @JvmOverloads constructor(
    override val projection: Projection,
    override val transform: ReadonlyTransform = SimpleTransform()
) : ICamera {

    override val viewMatrix = Matrix4.create()
        get() = Matrix4.ofView(
            this.transform.position.value,
            this.transform.rotation.value,
            field
        )
}