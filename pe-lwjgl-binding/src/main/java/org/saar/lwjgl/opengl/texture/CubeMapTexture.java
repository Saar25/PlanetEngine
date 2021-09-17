package org.saar.lwjgl.opengl.texture;

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
        texture.allocate(target, 1, InternalFormat.RGBA8, px.getWidth(), px.getHeight());

        allocate(texture, CubeMapFace.POSITIVE_X, px);
        allocate(texture, CubeMapFace.NEGATIVE_X, nx);
        allocate(texture, CubeMapFace.POSITIVE_Y, py);
        allocate(texture, CubeMapFace.NEGATIVE_Y, ny);
        allocate(texture, CubeMapFace.POSITIVE_Z, pz);
        allocate(texture, CubeMapFace.NEGATIVE_Z, nz);
        return new CubeMapTexture(texture);
    }

    private static void allocate(TextureObject texture, CubeMapFace face, TextureInfo info) {
        texture.loadCubeFace(face, 0, 0, 0, info.getWidth(), info.getHeight(),
                info.getFormatType(), info.getDataType(), info.getData());
    }

    public void applyParameters(TextureParameter... parameters) {
        this.texture.applyParameters(CubeMapTexture.target, parameters);
        this.texture.generateMipmap(target);
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
