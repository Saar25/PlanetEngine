package org.saar.core.screen

import org.saar.core.screen.image.ScreenImage
import org.saar.core.screen.image.SimpleScreenImage
import org.saar.lwjgl.opengl.fbo.IFbo
import org.saar.lwjgl.opengl.fbo.attachment.Attachment
import org.saar.lwjgl.opengl.fbo.attachment.AttachmentType
import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex
import org.saar.lwjgl.opengl.fbo.rendertarget.DrawRenderTargetComposite
import org.saar.lwjgl.opengl.fbo.rendertarget.IndexRenderTarget

object Screens {

    @JvmStatic
    fun fromPrototype(prototype: ScreenPrototype, fbo: IFbo, allocation: AllocationStrategy): OffScreen {
        val screenImagesMap = mutableMapOf<AttachmentIndex, ScreenImage>()

        val imagesPrototypes = prototype.screenImages.sortedBy { it.index.value }
        val colourImagesPrototypes = imagesPrototypes.filter { it.index.type == AttachmentType.COLOUR }

        imagesPrototypes.forEach { p ->
            val attachment = Attachment(p.buffer, allocation)
            fbo.addAttachment(p.index, attachment)

            screenImagesMap[p.index] = SimpleScreenImage(attachment)
        }

        setReadTarget(fbo, prototype)
        setDrawTargets(fbo, colourImagesPrototypes)

        return ScreenPrototypeWrapper(fbo, screenImagesMap)
    }

    private fun setReadTarget(fbo: IFbo, prototype: ScreenPrototype) {
        prototype.readIndex?.let {
            val target = IndexRenderTarget(it)
            fbo.setReadTarget(target)
        }
    }

    private fun setDrawTargets(fbo: IFbo, prototypes: List<ScreenImagePrototype>) {
        val drawRenderTargets = prototypes
            .filter { it.draw }
            .map { IndexRenderTarget(it.index) }

        val renderTarget = DrawRenderTargetComposite(drawRenderTargets)

        fbo.setDrawTarget(renderTarget)
    }
}