package org.saar.core.screen

import org.saar.lwjgl.opengl.fbo.IFbo
import org.saar.lwjgl.opengl.fbo.attachment.IAttachment
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex

class ScreenPrototypeWrapper(
    override val fbo: IFbo,
    override val attachments: Map<AttachmentIndex, IAttachment>
) : ScreenBase(), OffScreen
