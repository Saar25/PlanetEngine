package org.saar.lwjgl.opengl.vao.linking

import org.saar.lwjgl.opengl.attribute.Attributes
import org.saar.lwjgl.opengl.attribute.IAttribute

/**
 * Link the attributes, assuming that the data in the vbo is stored as [v1data, v2data, v3data...]
 * and not as [[ positions ], [ texture uvs ], [ normals ], ...]
 */
class InterleavedLinkingStrategy : LinkingStrategy {

    override fun link(attributes: Array<IAttribute>) {
        val stride = Attributes.sumBytes(*attributes)
        attributes.fold(0) { offset, attribute ->
            attribute.link(stride, offset)
            offset + attribute.bytesPerVertex
        }
    }
}