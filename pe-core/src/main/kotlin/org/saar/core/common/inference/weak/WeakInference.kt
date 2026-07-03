package org.saar.core.common.inference.weak

import org.saar.lwjgl.opengl.primitive.GlPrimitive

object WeakInference {

    @JvmStatic
    fun instance(vararg primitives: GlPrimitive): WeakInstance {
        return primitives.fold(WeakInstance.Builder()) { builder, primitive -> builder.with(primitive) }.build()
    }

    @JvmStatic
    fun vertex(vararg primitives: GlPrimitive): WeakVertex {
        return primitives.fold(WeakVertex.Builder()) { builder, primitive -> builder.with(primitive) }.build()
    }
}