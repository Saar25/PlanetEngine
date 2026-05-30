package org.saar.core.screen

import org.saar.lwjgl.opengl.fbo.attachment.index.ColourAttachmentIndex

interface ScreenPrototype {
    val screenImages: Collection<ScreenImagePrototype>
    val readIndex: ColourAttachmentIndex? get() = null
}
