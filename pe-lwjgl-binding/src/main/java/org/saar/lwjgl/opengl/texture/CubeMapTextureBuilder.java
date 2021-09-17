package org.saar.lwjgl.opengl.texture;

import org.saar.lwjgl.opengl.texture.parameter.TextureMagFilterParameter;
import org.saar.lwjgl.opengl.texture.parameter.TextureMinFilterParameter;
import org.saar.lwjgl.opengl.texture.parameter.TextureSWrapParameter;
import org.saar.lwjgl.opengl.texture.parameter.TextureTWrapParameter;
import org.saar.lwjgl.opengl.texture.values.MagFilterValue;
import org.saar.lwjgl.opengl.texture.values.MinFilterValue;
import org.saar.lwjgl.opengl.texture.values.WrapValue;

import java.io.IOException;

public class CubeMapTextureBuilder {

    private TextureInfo positiveX;
    private TextureInfo negativeX;
    private TextureInfo positiveY;
    private TextureInfo negativeY;
    private TextureInfo positiveZ;
    private TextureInfo negativeZ;

    public CubeMapTextureBuilder positiveX(String textureFile) throws IOException {
        this.positiveX = TextureLoader.load(textureFile);
        return this;
    }

    public CubeMapTextureBuilder negativeX(String textureFile) throws IOException {
        negativeX = TextureLoader.load(textureFile);
        return this;
    }

    public CubeMapTextureBuilder positiveY(String textureFile) throws IOException {
        positiveY = TextureLoader.load(textureFile);
        return this;
    }

    public CubeMapTextureBuilder negativeY(String textureFile) throws IOException {
        negativeY = TextureLoader.load(textureFile);
        return this;
    }

    public CubeMapTextureBuilder positiveZ(String textureFile) throws IOException {
        positiveZ = TextureLoader.load(textureFile);
        return this;
    }

    public CubeMapTextureBuilder negativeZ(String textureFile) throws IOException {
        negativeZ = TextureLoader.load(textureFile);
        return this;
    }

    public CubeMapTexture create() {
        final CubeMapTexture texture = CubeMapTexture.of(this.positiveX, this.positiveY,
                this.positiveZ, this.negativeX, this.negativeY, this.negativeZ);
        texture.applyParameters(
                new TextureMinFilterParameter(MinFilterValue.LINEAR),
                new TextureMagFilterParameter(MagFilterValue.LINEAR),
                new TextureSWrapParameter(WrapValue.CLAMP_TO_EDGE),
                new TextureTWrapParameter(WrapValue.CLAMP_TO_EDGE));
        texture.generateMipmap();
        return texture;
    }

}
