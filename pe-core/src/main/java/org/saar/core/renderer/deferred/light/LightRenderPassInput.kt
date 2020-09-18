package org.saar.core.renderer.deferred.light

import org.saar.core.renderer.deferred.RenderPassInput
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture

class LightRenderPassInput(val normalTexture: ReadOnlyTexture,
                           val depthTexture: ReadOnlyTexture) : RenderPassInput