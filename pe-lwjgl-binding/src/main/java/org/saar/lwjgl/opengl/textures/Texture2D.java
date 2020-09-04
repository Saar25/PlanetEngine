package org.saar.lwjgl.opengl.textures;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.IFormatType;
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter;
import org.saar.lwjgl.opengl.textures.parameters.MinFilterParameter;
import org.saar.lwjgl.opengl.textures.settings.*;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Texture2D implements ReadOnlyTexture {

    private static final TextureTarget target = TextureTarget.TEXTURE_2D;

    private final Texture texture;
    private final int width;
    private final int height;

    private final List<TextureSetting> settings = new ArrayList<>();

    public Texture2D(Texture texture) {
        this.texture = texture;
        this.width = texture.getWidth();
        this.height = texture.getHeight();
    }

    public Texture2D(int width, int height) {
        this.texture = Texture.create(target);
        this.width = width;
        this.height = height;

        allocate(FormatType.RGBA8, FormatType.RGBA, DataType.U_BYTE);
    }

    public static Texture2D of(int width, int height) {
        return new Texture2D(width, height);
    }

    public static Texture2D of(String fileName) throws Exception {
        ReadOnlyTexture cached = TextureCache.getTexture(fileName);
        if (cached instanceof Texture2D) {
            return (Texture2D) cached;
        } else if (cached instanceof Texture) {
            return new Texture2D((Texture) cached);
        }
        final TextureInfo info = TextureLoader.load(fileName);
        final Texture texture = Texture.create(Texture2D.target);

        texture.allocate(Texture2D.target, 0, FormatType.RGBA8, info.getWidth(),
                info.getHeight(), 0, info.getFormatType(), DataType.U_BYTE, info.getData());

        TextureCache.addToCache(fileName, texture);

        final Texture2D texture2D = new Texture2D(texture);

        texture2D.setSettings(
                new TextureMinFilterSetting(MinFilterParameter.NEAREST_MIPMAP_LINEAR),
                new TextureMagFilterSetting(MagFilterParameter.LINEAR),
                new TextureAnisotropicFilterSetting(4f),
                new TextureMipMapSetting()
        );

        return texture2D;
    }

    public void setSettings(TextureSetting... settings) {
        this.settings.clear();
        this.settings.addAll(Arrays.asList(settings));
        applySettings();
    }

    private void applySettings() {
        this.texture.setSettings(Texture2D.target,
                this.settings.toArray(new TextureSetting[0]));
    }

    /**
     * Allocates memory for the texture
     */
    private void allocate(IFormatType internalFormat, IFormatType format, DataType dataType) {
        this.texture.allocate(target, 0, internalFormat, getWidth(),
                getHeight(), 0, format, dataType, null);
        applySettings();
    }

    /**
     * Loads the given data to the texture
     *
     * @param data     the texture data
     * @param format   the data format
     * @param dataType the data type
     */
    public void load(ByteBuffer data, IFormatType format, DataType dataType) {
        this.texture.load(Texture2D.target, 0, 0, 0, getWidth(), getHeight(),
                format, dataType, data);
        applySettings();
    }

    /**
     * Loads the data of the given file to the texture
     *
     * @param textureFile the texture file
     * @throws Exception thrown when could not load the texture file
     */
    public void load(String textureFile) throws Exception {
        final TextureInfo info = TextureLoader.load(textureFile);
        this.load(info.getData(), info.getFormatType(), info.getDataType());
    }

    /**
     * Returns the width of the texture
     *
     * @return the width of the texture
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the texture
     *
     * @return the height of the texture
     */
    public int getHeight() {
        return this.height;
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
