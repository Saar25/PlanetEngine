package org.saar.core.screen

import org.saar.lwjgl.opengl.fbo.IFbo
import org.saar.lwjgl.opengl.fbo.attachment.IAttachment
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex
import org.saar.lwjgl.opengl.fbo.attachment.index.ColorAttachmentIndex
import org.saar.lwjgl.opengl.fbo.rendertarget.DrawRenderTargetComposite
import org.saar.lwjgl.opengl.fbo.rendertarget.IndexRenderTarget

class MutableScreen(override val fbo: IFbo, width: Int = 1, height: Int = 1) : FboScreen(), OffScreen {

    private var _width: Int = width
    override val width get() = this._width

    private var _height: Int = height
    override val height get() = this._height

    private val attachments = mutableMapOf<AttachmentIndex, IAttachment>()

    override fun resize(width: Int, height: Int) {
        this.fbo.bind()
        this._width = width
        this._height = height
        this.attachments.values.forEach { it.allocate(width, height) }
    }

    override fun delete() {
        this.fbo.delete()
        this.attachments.values.forEach(IAttachment::delete)
    }

    fun addAttachment(index: AttachmentIndex, attachment: IAttachment) {
        this.attachments[index]?.delete()
        attachment.allocate(this.width, this.height)
        this.fbo.addAttachment(index, attachment)
        this.attachments[index] = attachment
    }

    fun removeAttachment(index: AttachmentIndex) {
        this.attachments.remove(index)?.delete()
    }

    fun setReadImage(index: ColorAttachmentIndex) {
        val target = IndexRenderTarget(index)
        this.fbo.setReadTarget(target)
    }

    fun setDrawImages(indices: Iterable<ColorAttachmentIndex>) {
        val targets = indices.map(::IndexRenderTarget)
        val target = DrawRenderTargetComposite(targets)
        this.fbo.setDrawTarget(target)
    }
}
