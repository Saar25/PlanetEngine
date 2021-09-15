package org.saar.lwjgl.opengl.textures;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.textures.settings.TextureSetting;
import org.saar.lwjgl.opengl.utils.GlConfigs;

import java.nio.ByteBuffer;

public class Texture implements ITexture {

    public static final Texture NULL = new Texture(0, TextureTarget.TEXTURE_2D);

    private final int id;
    private final TextureTarget target;

    private boolean deleted = false;

    private Texture(int id, TextureTarget target) {
        this.id = id;
        this.target = target;
        this.bind();
    }

    public static Texture create(TextureTarget target) {
        final int id = GL11.glGenTextures();
        return new Texture(id, target);
    }

    public static Texture create() {
        return Texture.create(TextureTarget.TEXTURE_2D);
    }

    /**
     * Binds the given texture to the given texture unit
     * this method takes care of null check so it is the same as
     * if (texture != null) texture.bind(unit);
     *
     * @param texture the texture to bind
     * @param unit    the unit to bind to
     */
    public static void bind(ReadOnlyTexture texture, int unit) {
        if (texture != null) {
            texture.bind(unit);
        }
    }

    /**
     * Unbinds the texture on the given unit
     *
     * @param unit the unit to unbind
     */
    public static void unbind(int unit) {
        Texture.NULL.bind(unit);
    }

    public void setSettings(TextureTarget target, TextureSetting... settings) {
        bind();
        for (TextureSetting setting : settings) {
            setting.apply(target);
        }
    }

    @Override
    public void attachToFbo(int attachment, int level) {
        GL32.glFramebufferTexture(GL30.GL_FRAMEBUFFER, attachment, id, level);
    }

    @Override
    public void allocate(TextureTarget target, int level, InternalFormat internalFormat, int width,
                         int height, int border, FormatType format, DataType type, ByteBuffer data) {
        bind();
        GL11.glTexImage2D(target.get(), level, internalFormat.get(), width,
                height, border, format.get(), type.get(), data);
    }

    @Override
    public void allocateMultisample(TextureTarget target, int samples, InternalFormat iFormat,
                                    int width, int height, boolean fixedSampleLocations) {
        bind();
        GL32.glTexImage2DMultisample(target.get(), samples,
                iFormat.get(), width, height, fixedSampleLocations);
    }

    @Override
    public void load(TextureTarget target, int level, int xOffset, int yOffset, int width,
                     int height, FormatType format, DataType type, ByteBuffer data) {
        bind();
        GL11.glTexSubImage2D(target.get(), level, xOffset, yOffset, width,
                height, format.get(), type.get(), data);
    }

    public int getWidth() {
        bind();
        return GL11.glGetTexLevelParameteri(this.target.get(), 0, GL11.GL_TEXTURE_WIDTH);
    }

    public int getHeight() {
        bind();
        return GL11.glGetTexLevelParameteri(this.target.get(), 0, GL11.GL_TEXTURE_HEIGHT);
    }

    public void getPixelsBuffer(FormatType format, DataType dataType, ByteBuffer buffer) {
        GL11.glGetTexImage(this.target.get(), 0, format.get(), dataType.get(), buffer);
    }

    @Override
    public void bind(int unit) {
        active(unit);
        bind();
    }

    public void active(int unit) {
        if (!GlConfigs.CACHE_STATE || ActiveTexture.isActive(unit)) {
            GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
            ActiveTexture.set(unit);
        }
    }

    @Override
    public void bind() {
        if (!GlConfigs.CACHE_STATE || BoundTexture.isBound(this.target, this.id)) {
            GL11.glBindTexture(this.target.get(), this.id);
            BoundTexture.set(this.target, this.id);
        }
    }

    @Override
    public void unbind() {
        Texture.NULL.bind();
    }

    @Override
    public void delete() {
        if (!GlConfigs.CACHE_STATE || !this.deleted) {
            GL11.glDeleteTextures(id);
            this.deleted = true;
        }
    }
}
