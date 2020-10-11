package org.saar.core.common.inference.weak

import org.saar.lwjgl.opengl.primitive.GlPrimitive

object WeakInference {

    @JvmStatic
    fun instance(vararg primitives: GlPrimitive): WeakInstance {
        val instance = WeakInstance()
        for (primitive in primitives) {
            instance.with(primitive)
        }
        return instance
    }

    @JvmStatic
    fun vertex(vararg primitives: GlPrimitive): WeakVertex {
        val instance = WeakVertex()
        for (primitive in primitives) {
            instance.with(primitive)
        }
        return instance
    }

}