package org.saar.lwjgl.opengl.fbos.attachment;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.saar.lwjgl.opengl.fbos.attachment.index.AttachmentIndex;

import java.util.Arrays;

public final class Attachments {

    private Attachments() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static void drawAttachments(Attachment... attachments) {
        final AttachmentIndex[] indices = Arrays.stream(attachments)
                .map(Attachment::getIndex)
                .toArray(AttachmentIndex[]::new);
        drawAttachments(indices);
    }

    public static void readAttachment(Attachment attachment) {
        readAttachment(attachment.getIndex());
    }

    public static void drawAttachments(AttachmentIndex... indices) {
        final int[] buffers = Arrays.stream(indices)
                .mapToInt(AttachmentIndex::getValue)
                .toArray();
        Attachments.drawBuffers(buffers);
    }

    public static void readAttachment(AttachmentIndex index) {
        Attachments.readBuffer(index.getValue());
    }

    private static void drawBuffers(int... buffers) {
        GL20.glDrawBuffers(buffers);
    }

    private static void readBuffer(int buffer) {
        GL11.glReadBuffer(buffer);
    }
}
