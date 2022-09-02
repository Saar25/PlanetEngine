package org.saar.core.screen

import org.saar.core.screen.image.ScreenImage
import org.saar.core.screen.image.SimpleScreenImage
import org.saar.lwjgl.opengl.fbo.IFbo
import org.saar.lwjgl.opengl.fbo.attachment.Attachment
import org.saar.lwjgl.opengl.fbo.attachment.AttachmentType
import org.saar.lwjgl.opengl.fbo.attachment.allocation.AllocationStrategy
import org.saar.lwjgl.opengl.fbo.attachment.index.AttachmentIndex
import org.saar.lwjgl.opengl.fbo.attachment.index.BasicAttachmentIndex
import org.saar.lwjgl.opengl.fbo.attachment.index.ColourAttachmentIndex
import org.saar.lwjgl.opengl.fbo.rendertarget.DrawRenderTargetComposite
import org.saar.lwjgl.opengl.fbo.rendertarget.IndexRenderTarget

object Screens {

    @JvmStatic
    fun fromPrototype(prototype: ScreenPrototype, fbo: IFbo, allocation: AllocationStrategy): OffScreen {
        val locator = ScreenImagesLocator(prototype)
        val screenImagesMap = mutableMapOf<AttachmentIndex, ScreenImage>()

        val imagesPrototypes = locator.screenImagePrototypes.sortedBy { it.index.value }
        val colourImagesPrototypes = imagesPrototypes.filter { it.index.type == AttachmentType.COLOUR }
        val otherImagesPrototypes = imagesPrototypes.filter { it.index.type != AttachmentType.COLOUR }

        colourImagesPrototypes.forEachIndexed { i, p ->
            val index = ColourAttachmentIndex(i)
            val attachment = Attachment(p.buffer, allocation)
            fbo.addAttachment(index, attachment)

            screenImagesMap[index] = SimpleScreenImage(attachment)
        }

        otherImagesPrototypes.forEach { p ->
            val index = BasicAttachmentIndex(p.index.type)
            val attachment = Attachment(p.buffer, allocation)
            fbo.addAttachment(index, attachment)

            screenImagesMap[index] = SimpleScreenImage(attachment)
        }

        setReadTarget(fbo, colourImagesPrototypes)
        setDrawTargets(fbo, colourImagesPrototypes)

        return ScreenPrototypeWrapper(fbo, screenImagesMap)
    }

    private fun setReadTarget(fbo: IFbo, prototypes: List<ScreenImagePrototype>) {
        val readPrototypeIndex = prototypes.indexOfFirst { it.read }
        if (readPrototypeIndex >= 0) {
            val readIndex = ColourAttachmentIndex(readPrototypeIndex)
            fbo.setReadTarget(IndexRenderTarget(readIndex))
        }
    }

    private fun setDrawTargets(fbo: IFbo, prototypes: List<ScreenImagePrototype>) {
        val drawRenderTargets = prototypes
            .foldIndexed(arrayOf<AttachmentIndex>()) { index, indices, imagePrototype ->
                if (imagePrototype.draw) indices + ColourAttachmentIndex(index) else indices
            }.map { IndexRenderTarget(it) }

        val renderTarget = DrawRenderTargetComposite(*drawRenderTargets.toTypedArray())

        fbo.setDrawTarget(renderTarget)
    }
}