package org.saar.lwjgl.opengl.texture;

import org.lwjgl.opengl.*;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.texture.parameter.TextureParameter;
import org.saar.lwjgl.opengl.utils.GlConfigs;

import java.nio.ByteBuffer;

public class TextureObject {

    public static final TextureObject NULL = new TextureObject(0);

    private final int id;
    private boolean deleted = false;

    private TextureObject(int id) {
        this.id = id;
    }

    public static TextureObject create() {
        final int id = GL11.glGenTextures();
        return new TextureObject(id);
    }

    public void allocateMutable(TextureTarget target, int level, InternalFormat internalFormat, int width,
                                int height, int border, FormatType format, DataType type, ByteBuffer data) {
        bind(target);
        GL11.glTexImage2D(target.get(), level, internalFormat.get(),
                width, height, border, format.get(), type.get(), data);
    }

    public void allocateMutableMultisample(TextureTarget target, int samples, InternalFormat iFormat,
                                           int width, int height, boolean fixedSampleLocations) {
        bind(target);
        GL32.glTexImage2DMultisample(target.get(), samples,
                iFormat.get(), width, height, fixedSampleLocations);
    }

    public void allocate(TextureTarget target, int levels, InternalFormat internalFormat, int width, int height) {
        bind(target);
        GL42.glTexStorage2D(target.get(), levels, internalFormat.get(), width, height);
    }

    public void allocateMultisample(TextureTarget target, int levels, InternalFormat internalFormat,
                                    int width, int height, boolean fixedSampleLocations) {
        bind(target);
        GL43.glTexStorage2DMultisample(target.get(), levels, internalFormat.get(), width, height, fixedSampleLocations);
    }

    public void load(TextureTarget target, int level, int xOffset, int yOffset, int width,
                     int height, FormatType format, DataType type, ByteBuffer data) {
        bind(target);
        GL11.glTexSubImage2D(target.get(), level, xOffset, yOffset,
                width, height, format.get(), type.get(), data);
    }

    public void applyParameters(TextureTarget target, TextureParameter... parameters) {
        bind(target);
        for (TextureParameter setting : parameters) {
            setting.apply(target);
        }
    }

    public void generateMipmap(TextureTarget target) {
        bind(target);
        GL30.glGenerateMipmap(target.get());
    }

    public void attachToFbo(int attachment, int level) {
        GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, attachment, this.id, level);
    }

    public int getWidth(TextureTarget target, int level) {
        bind(target);
        return GL11.glGetTexLevelParameteri(target.get(), level, GL11.GL_TEXTURE_WIDTH);
    }

    public int getHeight(TextureTarget target, int level) {
        bind(target);
        return GL11.glGetTexLevelParameteri(target.get(), level, GL11.GL_TEXTURE_HEIGHT);
    }

    public void bind(TextureTarget target, int unit) {
        active(unit);
        bind(target);
    }

    public void active(int unit) {
        if (!GlConfigs.CACHE_STATE || ActiveTexture.isActive(unit)) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
            ActiveTexture.set(unit);
        }
    }

    public void bind(TextureTarget target) {
        if (!GlConfigs.CACHE_STATE || BoundTexture.isBound(target, this.id)) {
            GL11.glBindTexture(target.get(), this.id);
            BoundTexture.set(target, this.id);
        }
    }

    public void unbind(TextureTarget target) {
        TextureObject.NULL.bind(target);
    }

    public void delete() {
        if (!GlConfigs.CACHE_STATE || !this.deleted) {
            GL11.glDeleteTextures(this.id);
            this.deleted = true;
        }
    }
}
