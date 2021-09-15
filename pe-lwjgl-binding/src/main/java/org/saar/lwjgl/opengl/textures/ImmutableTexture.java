package org.saar.lwjgl.opengl.textures;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.textures.settings.TextureSetting;

import java.nio.ByteBuffer;

public class ImmutableTexture implements ITexture {

    private Texture texture = Texture.NULL;

    private ImmutableTexture() {
        this.bind();
    }


    public static ImmutableTexture create() {
        return new ImmutableTexture();
    }

    public void setSettings(TextureTarget target, TextureSetting... settings) {
        this.texture.setSettings(target, settings);
    }

    @Override
    public void attachToFbo(int attachment, int level) {
        this.texture.attachToFbo(attachment, level);
    }

    @Override
    public void allocate(TextureTarget target, int level, InternalFormat internalFormat, int width,
                         int height, int border, FormatType format, DataType type, ByteBuffer data) {
        this.texture.delete();
        this.texture = Texture.create(target);
        this.texture.allocateStorage(target, 1, internalFormat, width, height);
    }

    @Override
    public void allocateMultisample(TextureTarget target, int samples, InternalFormat internalFormat,
                                    int width, int height, boolean fixedSampleLocations) {
        this.texture.delete();
        this.texture = Texture.create(target);
        this.texture.allocateStorageMultisample(target, 0, internalFormat, width, height, fixedSampleLocations);
    }

    @Override
    public void allocateStorage(TextureTarget target, int levels, InternalFormat internalFormat, int width, int height) {
        this.texture.delete();
        this.texture = Texture.create(target);
        this.texture.allocateStorage(target, levels, internalFormat, width, height);
    }

    @Override
    public void allocateStorageMultisample(TextureTarget target, int levels, InternalFormat internalFormat,
                                           int width, int height, boolean fixedSampleLocations) {
        this.texture.delete();
        this.texture = Texture.create(target);
        this.texture.allocateStorageMultisample(target, 0, internalFormat, width, height, fixedSampleLocations);
    }

    @Override
    public void load(TextureTarget target, int level, int xOffset, int yOffset, int width,
                     int height, FormatType format, DataType type, ByteBuffer data) {
        this.texture.load(target, level, xOffset, yOffset, width, height, format, type, data);
    }

    public int getWidth() {
        return this.texture.getWidth();
    }

    public int getHeight() {
        return this.texture.getHeight();
    }

    @Override
    public void bind(int unit) {
        this.texture.bind(unit);
    }

    @Override
    public void bind() {
        this.texture.bind();
    }

    @Override
    public void unbind() {
        this.texture.unbind();
    }

    @Override
    public void delete() {
        this.texture.delete();
    }
}
