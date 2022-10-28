package org.saar.lwjgl.opengl.provokingvertex

import org.lwjgl.opengl.GL32

enum class ProvokingVertexValue(val value: Int) {

    FIRST(GL32.GL_FIRST_VERTEX_CONVENTION),
    LAST(GL32.GL_LAST_VERTEX_CONVENTION),

}