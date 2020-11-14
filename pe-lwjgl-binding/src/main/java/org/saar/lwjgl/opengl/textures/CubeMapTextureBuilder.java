package org.saar.lwjgl.opengl.textures;

import org.saar.lwjgl.opengl.textures.loader.TextureLoader;
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter;
import org.saar.lwjgl.opengl.textures.parameters.MinFilterParameter;
import org.saar.lwjgl.opengl.textures.parameters.WrapParameter;
import org.saar.lwjgl.opengl.textures.settings.TextureMagFilterSetting;
import org.saar.lwjgl.opengl.textures.settings.TextureMinFilterSetting;
import org.saar.lwjgl.opengl.textures.settings.TextureSWrapSetting;
import org.saar.lwjgl.opengl.textures.settings.TextureTWrapSetting;

public class CubeMapTextureBuilder {

    private TextureInfo positiveX;
    private TextureInfo negativeX;
    private TextureInfo positiveY;
    private TextureInfo negativeY;
    private TextureInfo positiveZ;
    private TextureInfo negativeZ;

    /**
     * Sets the texture on the positive x
     *
     * @param textureFile the texture's file
     * @return this
     */
    public CubeMapTextureBuilder positiveX(String textureFile) throws Exception {
        positiveX = TextureLoader.load(textureFile);
        return this;
    }

    /**
     * Sets the texture on the negative x
     *
     * @param textureFile the texture's file
     * @return this
     */
    public CubeMapTextureBuilder negativeX(String textureFile) throws Exception {
        negativeX = TextureLoader.load(textureFile);
        return this;
    }

    /**
     * Sets the texture on the positive y
     *
     * @param textureFile the texture's file
     * @return this
     */
    public CubeMapTextureBuilder positiveY(String textureFile) throws Exception {
        positiveY = TextureLoader.load(textureFile);
        return this;
    }

    /**
     * Sets the texture on the negative y
     *
     * @param textureFile the texture's file
     * @return this
     */
    public CubeMapTextureBuilder negativeY(String textureFile) throws Exception {
        negativeY = TextureLoader.load(textureFile);
        return this;
    }

    /**
     * Sets the texture on the positive z
     *
     * @param textureFile the texture's file
     * @return this
     */
    public CubeMapTextureBuilder positiveZ(String textureFile) throws Exception {
        positiveZ = TextureLoader.load(textureFile);
        return this;
    }

    /**
     * Sets the texture on the negative z
     *
     * @param textureFile the texture's file
     * @return this
     */
    public CubeMapTextureBuilder negativeZ(String textureFile) throws Exception {
        negativeZ = TextureLoader.load(textureFile);
        return this;
    }

    public CubeMapTexture create() {
        final CubeMapTexture texture = CubeMapTexture.of(this.positiveX, this.positiveY,
                this.positiveZ, this.negativeX, this.negativeY, this.negativeZ);
        texture.setSettings(
                new TextureMinFilterSetting(MinFilterParameter.LINEAR),
                new TextureMagFilterSetting(MagFilterParameter.LINEAR),
                new TextureSWrapSetting(WrapParameter.CLAMP_TO_EDGE),
                new TextureTWrapSetting(WrapParameter.CLAMP_TO_EDGE));
        return texture;
    }

}
