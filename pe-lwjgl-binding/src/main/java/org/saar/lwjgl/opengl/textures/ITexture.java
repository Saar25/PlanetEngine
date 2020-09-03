package org.saar.lwjgl.opengl.textures;

public interface ITexture extends WritableTexture, ReadOnlyTexture {

    /**
     * Attaches the texture to the bound fbo
     *
     * @param attachment the attachment of the texture
     * @param level      the mip map level of texture to attach
     */
    void attachToFbo(int attachment, int level);

    /**
     * Deletes the texture
     */
    void delete();

}
