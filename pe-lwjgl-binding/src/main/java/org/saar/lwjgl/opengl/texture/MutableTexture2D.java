package org.saar.lwjgl.opengl.texture;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.texture.parameter.TextureParameter;

import java.nio.ByteBuffer;

public class MutableTexture2D implements WritableTexture2D {

    private static final TextureTarget target = TextureTarget.TEXTURE_2D;

    private final TextureObject texture;
    private int width;
    private int height;

    public MutableTexture2D(TextureObject texture) {
        this.texture = texture;
    }

    public static MutableTexture2D create() {
        final TextureObject texture = TextureObject.create();
        return new MutableTexture2D(texture);
    }

    public void allocate(int level, InternalFormat internalFormat, int width, int height) {
        allocate(level, internalFormat, width, height, 0, FormatType.RGBA, DataType.U_BYTE, null);
    }

    public void allocate(int level, InternalFormat internalFormat, int width,
                         int height, int border, FormatType format, DataType type, ByteBuffer data) {
        this.texture.allocateMutable(MutableTexture2D.target, level,
                internalFormat, width, height, border, format, type, data);
        this.width = width;
        this.height = height;
    }

    public void allocateMultisample(int samples, InternalFormat internalFormat, int width,
                                    int height, boolean fixedSampleLocations) {
        this.texture.allocateMutableMultisample(MutableTexture2D.target, samples,
                internalFormat, width, height, fixedSampleLocations);
        this.width = width;
        this.height = height;
    }

    @Override
    public void applyParameters(TextureParameter... parameters) {
        this.texture.applyParameters(MutableTexture2D.target, parameters);
    }

    @Override
    public void generateMipmap() {
        this.texture.generateMipmap(MutableTexture2D.target);
    }

    @Override
    public void load(int level, int xOffset, int yOffset, int width, int height, FormatType format, DataType type, ByteBuffer data) {
        this.texture.load(MutableTexture2D.target, level, xOffset, yOffset, width, height, format, type, data);
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
        this.texture.bind(MutableTexture2D.target, unit);
    }

    @Override
    public void bind() {
        this.texture.bind(MutableTexture2D.target);
    }

    @Override
    public void unbind() {
        this.texture.unbind(MutableTexture2D.target);
    }

    @Override
    public void delete() {
        this.texture.delete();
    }

    public void attachToFbo(int attachment, int level) {
        this.texture.attachToFbo(attachment, level);
    }
}
