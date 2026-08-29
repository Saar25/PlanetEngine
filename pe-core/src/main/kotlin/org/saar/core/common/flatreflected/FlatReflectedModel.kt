package org.saar.core.common.flatreflected

import org.joml.Vector3f
import org.saar.core.mesh.Mesh
import org.saar.core.mesh.Model
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.maths.transform.SimpleTransform

class FlatReflectedModel(
    override val mesh: Mesh,
    val normal: Vector3f,
    val transform: SimpleTransform,
) : Model {

    var reflectionMap: ReadOnlyTexture = Texture2D.NULL

    constructor(mesh: Mesh, normal: Vector3f) : this(mesh, normal, SimpleTransform())

    override fun delete() {
        super.delete()
        this.reflectionMap.delete()
    }
}