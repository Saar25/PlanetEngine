package org.saar.core.common.flatreflected

import org.joml.Vector3f
import org.saar.core.mesh.Model
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.lwjgl.opengl.texture.Texture2D
import org.saar.maths.objects.Planef
import org.saar.maths.transform.SimpleTransform

class FlatReflectedModel(
    override val mesh: FlatReflectedMesh,
    val normal: Vector3f,
    val transform: SimpleTransform) : Model {

    var reflectionMap: ReadOnlyTexture = Texture2D.NULL

    constructor(mesh: FlatReflectedMesh, normal: Vector3f) : this(mesh, normal, SimpleTransform())

    fun toPlane(): Planef {
        val position = this.transform.position.value
        return Planef(position, this.normal)
    }

    override fun delete() {
        super.delete()
        this.reflectionMap.delete()
    }
}