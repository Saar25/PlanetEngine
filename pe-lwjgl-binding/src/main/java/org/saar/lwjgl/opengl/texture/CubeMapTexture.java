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
        texture.allocate(CubeMapTexture.target, 1, InternalFormat.RGBA8, px.getWidth(), px.getHeight());

        final CubeMapTexture cubeMapTexture = new CubeMapTexture(texture);
        cubeMapTexture.load(CubeMapFace.POSITIVE_X, px);
        cubeMapTexture.load(CubeMapFace.NEGATIVE_X, nx);
        cubeMapTexture.load(CubeMapFace.POSITIVE_Y, py);
        cubeMapTexture.load(CubeMapFace.NEGATIVE_Y, ny);
        cubeMapTexture.load(CubeMapFace.POSITIVE_Z, pz);
        cubeMapTexture.load(CubeMapFace.NEGATIVE_Z, nz);
        return cubeMapTexture;
    }

    private void load(CubeMapFace face, TextureInfo info) {
        this.texture.loadCubeFace(face, 0, 0, 0, info.getWidth(), info.getHeight(),
                info.getFormatType(), info.getDataType(), info.getData());
    }

    @Override
    public void applyParameters(TextureParameter... parameters) {
        this.texture.applyParameters(CubeMapTexture.target, parameters);
    }

    @Override
    public void generateMipmap() {
        this.texture.generateMipmap(CubeMapTexture.target);
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
