package org.saar.lwjgl.opengl.vao.linking

import org.saar.lwjgl.opengl.attribute.IAttribute

interface LinkingStrategy {

    fun link(attributes: Array<IAttribute>)

}