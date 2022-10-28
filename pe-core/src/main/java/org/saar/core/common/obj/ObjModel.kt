package org.saar.core.common.obj

import org.saar.core.mesh.Mesh
import org.saar.core.mesh.Model
import org.saar.lwjgl.opengl.texture.ReadOnlyTexture
import org.saar.maths.transform.SimpleTransform
import org.saar.maths.transform.Transform

class ObjModel @JvmOverloads constructor(
    override val mesh: Mesh,
    val texture: ReadOnlyTexture,
    val transform: Transform = SimpleTransform(),
) : Model