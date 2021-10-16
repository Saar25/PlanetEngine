package org.saar.lwjgl.opengl.fbos.attachment;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public final class Attachments {

    private Attachments() {
        throw new AssertionError("Cannot create instance of class "
                + getClass().getSimpleName());
    }

    public static void drawAttachments(Attachment... attachments) {
        Attachments.drawBuffers(attachmentsPoints(attachments));
    }

    public static void readAttachment(Attachment attachment) {
        Attachments.readBuffer(attachment.getIndex().get());
    }

    public static void drawBuffers(int... buffers) {
        GL20.glDrawBuffers(buffers);
    }

    public static void readBuffer(int buffer) {
        GL11.glReadBuffer(buffer);
    }

    private static int[] attachmentsPoints(Attachment... attachments) {
        final int[] buffer = new int[attachments.length];
        for (int i = 0; i < attachments.length; i++) {
            final Attachment attachment = attachments[i];
            buffer[i] = attachment.getIndex().get();
        }
        return buffer;
    }
}
