package org.saar.lwjgl.opengl.fbos.attachment;

public class AttachmentIndex {

    private final AttachmentType type;
    private final int index;

    private AttachmentIndex(AttachmentType type, int index) {
        this.type = type;
        this.index = index;
    }

    public static AttachmentIndex ofColour(int index) {
        return new AttachmentIndex(AttachmentType.COLOUR, index);
    }

    public static AttachmentIndex ofDepth() {
        return new AttachmentIndex(AttachmentType.DEPTH, 0);
    }

    public static AttachmentIndex ofStencil() {
        return new AttachmentIndex(AttachmentType.STENCIL, 0);
    }

    public static AttachmentIndex ofDepthStencil() {
        return new AttachmentIndex(AttachmentType.DEPTH_STENCIL, 0);
    }

    public int get() {
        return this.type.get() + this.index;
    }
}
