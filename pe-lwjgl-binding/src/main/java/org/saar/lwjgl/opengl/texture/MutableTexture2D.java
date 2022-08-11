package org.saar.lwjgl.opengl.texture;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.texture.parameter.TextureParameter;

import java.nio.ByteBuffer;

public class MutableTexture2D implements WritableTexture2D {


    private final TextureObject texture;
    private final TextureTarget target;
    private int width;
    private int height;

    private MutableTexture2D(TextureObject texture, TextureTarget target) {
        this.texture = texture;
        this.target = target;
    }

    public static MutableTexture2D create() {
        final TextureObject texture = TextureObject.create();
        return new MutableTexture2D(texture, TextureTarget.TEXTURE_2D);
    }

    public static MutableTexture2D createMultisample() {
        final TextureObject texture = TextureObject.create();
        return new MutableTexture2D(texture, TextureTarget.TEXTURE_2D_MULTISAMPLE);
    }

    public void allocate(int level, InternalFormat internalFormat, int width, int height) {
        allocate(level, internalFormat, width, height, 0, internalFormat.getFormatType(), internalFormat.getDataType(), null);
    }

    public void allocate(int level, InternalFormat internalFormat, int width,
                         int height, int border, FormatType format, DataType type, ByteBuffer data) {
        this.texture.allocateMutable(this.target, level, internalFormat,
                width, height, border, format, type, data);
        this.width = width;
        this.height = height;
    }

    public void allocateMultisample(int samples, InternalFormat internalFormat, int width,
                                    int height, boolean fixedSampleLocations) {
        this.texture.allocateMutableMultisample(this.target, samples,
                internalFormat, width, height, fixedSampleLocations);
        this.width = width;
        this.height = height;
    }

    @Override
    public void applyParameters(TextureParameter... parameters) {
        this.texture.applyParameters(this.target, parameters);
    }

    @Override
    public void generateMipmap() {
        this.texture.generateMipmap(this.target);
    }

    @Override
    public void load(int level, int xOffset, int yOffset, int width, int height, FormatType format, DataType type, ByteBuffer data) {
        this.texture.load(this.target, level, xOffset, yOffset, width, height, format, type, data);
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void bind(int unit) {
        this.texture.bind(this.target, unit);
    }

    @Override
    public void bind() {
        this.texture.bind(this.target);
    }

    @Override
    public void unbind() {
        this.texture.unbind(this.target);
    }

    @Override
    public void delete() {
        this.texture.delete();
    }

    public void attachToFbo(int attachment, int level) {
        this.texture.attachToFbo(attachment, level);
    }
}
