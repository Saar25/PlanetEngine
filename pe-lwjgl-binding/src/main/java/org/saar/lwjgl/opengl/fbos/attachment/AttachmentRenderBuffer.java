package attachment;

import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.RenderBuffer;
import org.saar.lwjgl.opengl.fbos.attachment.AttachmentBuffer;

public class AttachmentRenderBuffer implements AttachmentBuffer {

    private final RenderBuffer renderBuffer;

    public AttachmentRenderBuffer(RenderBuffer renderBuffer) {
        this.renderBuffer = renderBuffer;
    }

    private RenderBuffer getRenderBuffer() {
        return this.renderBuffer;
    }

    @Override
    public void allocate(int width, int height) {
        getRenderBuffer().loadStorage(width, height, FormatType.RGBA8);
    }

    @Override
    public void allocateMultisample(int width, int height, int samples) {
        getRenderBuffer().loadStorageMultisample(width, height, FormatType.RGBA8, samples);
    }

    @Override
    public void attachToFbo(int attachment) {
        getRenderBuffer().attachToFbo(attachment);
    }

    @Override
    public void delete() {
        getRenderBuffer().delete();
    }
}
