package org.saar.core.common.flatreflected

import org.joml.Vector3f
import org.saar.core.mesh.Model
import org.saar.maths.transform.SimpleTransform

class FlatReflectedModel(
    override val mesh: FlatReflectedMesh,
    val normal: Vector3f,
    val transform: SimpleTransform) : Model {

    constructor(mesh: FlatReflectedMesh, normal: Vector3f) : this(mesh, normal, SimpleTransform())
}