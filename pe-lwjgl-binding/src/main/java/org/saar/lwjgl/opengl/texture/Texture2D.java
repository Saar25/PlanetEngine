package org.saar.lwjgl.opengl.texture;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.texture.parameter.TextureAnisotropicFilterParameter;
import org.saar.lwjgl.opengl.texture.parameter.TextureMagFilterParameter;
import org.saar.lwjgl.opengl.texture.parameter.TextureMinFilterParameter;
import org.saar.lwjgl.opengl.texture.parameter.TextureParameter;
import org.saar.lwjgl.opengl.texture.values.MagFilterValue;
import org.saar.lwjgl.opengl.texture.values.MinFilterValue;

import java.nio.ByteBuffer;

public class Texture2D implements WritableTexture {

    public static final Texture2D NULL = new Texture2D(TextureObject.NULL, 0, 0);

    private static final TextureTarget target = TextureTarget.TEXTURE_2D;

    private final TextureObject texture;
    private final int width;
    private final int height;

    public Texture2D(TextureObject texture, int width, int height) {
        this.texture = texture;
        this.width = width;
        this.height = height;
    }

    public static Texture2D of(int width, int height) {
        return of(width, height, InternalFormat.RGBA8);
    }

    public static Texture2D of(int width, int height, InternalFormat format) {
        return of(width, height, format, 5);
    }

    public static Texture2D of(int width, int height, InternalFormat format, int levels) {
        final TextureObject texture = TextureObject.create();
        texture.allocate(Texture2D.target, levels, format, width, height);
        return new Texture2D(texture, width, height);
    }

    public static Texture2D of(String fileName) throws Exception {
        final ReadOnlyTexture cached = TextureCache.getTexture(fileName);
        if (cached instanceof Texture2D) return (Texture2D) cached;

        final TextureInfo info = TextureLoader.load(fileName);

        final Texture2D texture2D = Texture2D.of(info.getWidth(), info.getHeight());
        texture2D.load(0, info.getFormatType(), info.getDataType(), info.getData());
        TextureCache.addToCache(fileName, texture2D);

        texture2D.applyParameters(
                new TextureMinFilterParameter(MinFilterValue.NEAREST_MIPMAP_LINEAR),
                new TextureMagFilterParameter(MagFilterValue.LINEAR),
                new TextureAnisotropicFilterParameter(4f)
        );
        texture2D.generateMipmap();

        return texture2D;
    }

    public void applyParameters(TextureParameter... parameters) {
        this.texture.applyParameters(Texture2D.target, parameters);
    }

    public void generateMipmap() {
        this.texture.generateMipmap(Texture2D.target);
    }

    public void load(String textureFile) throws Exception {
        final TextureInfo info = TextureLoader.load(textureFile);
        load(0, info.getFormatType(), info.getDataType(), info.getData());
    }

    @Override
    public void load(int level, int xOffset, int yOffset, int width, int height, FormatType format, DataType type, ByteBuffer data) {
        this.texture.load(Texture2D.target, level, xOffset, yOffset, width, height, format, type, data);
    }

    public void load(int level, FormatType format, DataType type, ByteBuffer data) {
        this.texture.load(Texture2D.target, level, 0, 0, this.width, this.height, format, type, data);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    @Override
    public void bind(int unit) {
        this.texture.bind(Texture2D.target, unit);
    }

    @Override
    public void bind() {
        this.texture.bind(Texture2D.target);
    }

    @Override
    public void unbind() {
        this.texture.unbind(Texture2D.target);
    }

    @Override
    public void delete() {
        this.texture.delete();
    }
}
