package org.saar.lwjgl.opengl.textures;

import org.saar.lwjgl.opengl.textures.settings.TextureSetting;

public interface ITexture extends WritableTexture, ReadOnlyTexture {

    /**
     * Attaches the texture to the bound fbo
     *
     * @param attachment the attachment of the texture
     * @param level      the mip map level of texture to attach
     */
    void attachToFbo(int attachment, int level);

    /**
     * Sets the settings of the texture, clamping, min filter, etc...
     *
     * @param target   the texture target
     * @param settings the settings
     */
    void setSettings(TextureTarget target, TextureSetting... settings);

    /**
     * Deletes the texture
     */
    void delete();

}
