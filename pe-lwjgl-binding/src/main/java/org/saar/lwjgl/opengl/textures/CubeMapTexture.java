package org.saar.lwjgl.opengl.textures;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.textures.settings.TextureSetting;

public class CubeMapTexture implements ReadOnlyTexture {

    private static final TextureTarget target = TextureTarget.TEXTURE_CUBE_MAP;

    private final Texture texture;

    private CubeMapTexture(Texture texture) {
        this.texture = texture;
        this.bind();
    }

    public static CubeMapTexture create() {
        Texture texture = Texture.create(TextureTarget.TEXTURE_CUBE_MAP);
        return new CubeMapTexture(texture);
    }

    public static CubeMapTexture of(TextureInfo px, TextureInfo py, TextureInfo pz,
                                    TextureInfo nx, TextureInfo ny, TextureInfo nz) {
        final Texture texture = Texture.create(target);
        allocate(texture, TextureTarget.TEXTURE_CUBE_MAP_POSITIVE_X, px);
        allocate(texture, TextureTarget.TEXTURE_CUBE_MAP_NEGATIVE_X, nx);
        allocate(texture, TextureTarget.TEXTURE_CUBE_MAP_POSITIVE_Y, py);
        allocate(texture, TextureTarget.TEXTURE_CUBE_MAP_NEGATIVE_Y, ny);
        allocate(texture, TextureTarget.TEXTURE_CUBE_MAP_POSITIVE_Z, pz);
        allocate(texture, TextureTarget.TEXTURE_CUBE_MAP_NEGATIVE_Z, nz);
        return new CubeMapTexture(texture);
    }

    private static void allocate(Texture texture, TextureTarget target, TextureInfo info) {
        if (info != null) {
            texture.allocate(target, 0, FormatType.RGBA8, info.getWidth(),
                    info.getHeight(), 0, FormatType.RGBA, DataType.U_BYTE, info.getData());
        }
    }

    public static CubeMapTextureBuilder builder() {
        return new CubeMapTextureBuilder();
    }

    public void setSettings(TextureSetting... settings) {
        this.texture.setSettings(CubeMapTexture.target, settings);
    }

    private Texture getTexture() {
        return this.texture;
    }

    @Override
    public void bind(int unit) {
        getTexture().bind(unit);
    }

    @Override
    public void bind() {
        getTexture().bind();
    }

    @Override
    public void unbind() {
        getTexture().unbind();
    }

    @Override
    public void delete() {
        getTexture().delete();
    }
}
