package org.saar.rhi.renderpass

import org.saar.rhi.texture.TextureFormat

data class ClearValue(
    val r: Float = 0f,
    val g: Float = 0f,
    val b: Float = 0f,
    val a: Float = 0f,
    val depth: Float = 1f,
    val stencil: Int = 0,
)

data class RenderPassAttachment(
    val format: TextureFormat,
    val samples: Int = 1,
    val loadOp: LoadOp = LoadOp.CLEAR,
    val storeOp: StoreOp = StoreOp.STORE,
    val stencilLoadOp: LoadOp = LoadOp.DONT_CARE,
    val stencilStoreOp: StoreOp = StoreOp.DONT_CARE,
    val initialLayout: Int? = null,
    val finalLayout: Int? = null,
    val clearColor: ClearValue = ClearValue(),
)
