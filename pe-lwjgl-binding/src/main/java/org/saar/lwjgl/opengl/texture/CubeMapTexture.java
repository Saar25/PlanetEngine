package org.saar.lwjgl.opengl.texture;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.texture.parameter.TextureParameter;

public class CubeMapTexture implements ReadOnlyTexture {

    private static final TextureTarget target = TextureTarget.TEXTURE_CUBE_MAP;

    private final TextureObject texture;

    private CubeMapTexture(TextureObject texture) {
        this.texture = texture;
    }

    public static CubeMapTexture create() {
        final TextureObject texture = TextureObject.create();
        return new CubeMapTexture(texture);
    }

    public static CubeMapTexture of(TextureInfo px, TextureInfo py, TextureInfo pz,
                                    TextureInfo nx, TextureInfo ny, TextureInfo nz) {
        final TextureObject texture = TextureObject.create();
        allocate(texture, TextureTarget.TEXTURE_CUBE_MAP_POSITIVE_X, px);
        allocate(texture, TextureTarget.TEXTURE_CUBE_MAP_NEGATIVE_X, nx);
        allocate(texture, TextureTarget.TEXTURE_CUBE_MAP_POSITIVE_Y, py);
        allocate(texture, TextureTarget.TEXTURE_CUBE_MAP_NEGATIVE_Y, ny);
        allocate(texture, TextureTarget.TEXTURE_CUBE_MAP_POSITIVE_Z, pz);
        allocate(texture, TextureTarget.TEXTURE_CUBE_MAP_NEGATIVE_Z, nz);
        return new CubeMapTexture(texture);
    }

    private static void allocate(TextureObject texture, TextureTarget target, TextureInfo info) {
        texture.allocate(target, 0, InternalFormat.RGBA8, info.getWidth(), info.getHeight());
        texture.load(target, 0, 0, 0, info.getWidth(), info.getHeight(),
                FormatType.RGBA, DataType.U_BYTE, info.getData());
    }

    public void applyParameters(TextureParameter... parameters) {
        this.texture.applyParameters(CubeMapTexture.target, parameters);
    }

    @Override
    public void bind(int unit) {
        this.texture.bind(CubeMapTexture.target, unit);
    }

    @Override
    public void bind() {
        this.texture.bind(CubeMapTexture.target);
    }

    @Override
    public void unbind() {
        this.texture.unbind(CubeMapTexture.target);
    }

    @Override
    public void delete() {
        this.texture.delete();
    }
}
