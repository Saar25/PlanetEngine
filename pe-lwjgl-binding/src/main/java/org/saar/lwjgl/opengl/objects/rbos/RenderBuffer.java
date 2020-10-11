package org.saar.lwjgl.opengl.objects.rbos;

import org.lwjgl.opengl.GL30;
import org.saar.lwjgl.opengl.constants.IInternalFormat;
import org.saar.lwjgl.opengl.utils.GlConfigs;

public class RenderBuffer {

    private static final RenderBuffer NULL = new RenderBuffer(0);

    private final int id;

    private boolean deleted;

    private RenderBuffer(int id) {
        this.id = id;
    }

    public static RenderBuffer create() {
        final int id = GL30.glGenRenderbuffers();
        return new RenderBuffer(id);
    }

    public void loadStorage(int width, int height, IInternalFormat iFormat) {
        bind();
        GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER,
                iFormat.get(), width, height);
    }

    public void loadStorageMultisample(int width, int height, IInternalFormat iFormat, int samples) {
        bind();
        GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER,
                samples, iFormat.get(), width, height);
    }

    public void attachToFbo(int attachment) {
        bind();
        GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER,
                attachment, GL30.GL_RENDERBUFFER, this.id);
    }

    public void bind() {
        if (!GlConfigs.CACHE_STATE || BoundRbo.isBound(this.id)) {
            GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, id);
            BoundRbo.set(this.id);
        }
    }

    public void unbind() {
        RenderBuffer.NULL.bind();
    }

    public void delete() {
        if (!GlConfigs.CACHE_STATE || !this.deleted) {
            GL30.glDeleteRenderbuffers(id);
            this.deleted = true;
        }
    }
}
