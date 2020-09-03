package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.textures.Texture;
import org.saar.lwjgl.opengl.textures.TextureTarget;
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter;
import org.saar.lwjgl.opengl.textures.parameters.MinFilterParameter;
import org.saar.lwjgl.opengl.textures.settings.TextureAnisotropicFilterSetting;
import org.saar.lwjgl.opengl.textures.settings.TextureMagFilterSetting;
import org.saar.lwjgl.opengl.textures.settings.TextureMinFilterSetting;
import org.saar.lwjgl.opengl.textures.settings.TextureMipMapSetting;

public class AttachmentTextureBuffer implements AttachmentBuffer {

    private final Texture texture;

    public AttachmentTextureBuffer(Texture texture) {
        this.texture = texture;

        getTexture().setSettings(TextureTarget.TEXTURE_2D,
                new TextureMipMapSetting(),
                new TextureAnisotropicFilterSetting(4f),
                new TextureMagFilterSetting(MagFilterParameter.LINEAR),
                new TextureMinFilterSetting(MinFilterParameter.NEAREST));
    }

    private Texture getTexture() {
        return this.texture;
    }

    @Override
    public void allocate(int width, int height) {
        getTexture().allocate(TextureTarget.TEXTURE_2D, 0, FormatType.RGBA8,
                width, height, 0, FormatType.RGBA, DataType.U_BYTE, null);
    }

    @Override
    public void allocateMultisample(int width, int height, int samples) {
        getTexture().allocateMultisample(samples, FormatType.RGBA8, width, height);
    }

    @Override
    public void attachToFbo(int attachment) {
        getTexture().attachToFbo(attachment, 0);
    }

    @Override
    public void delete() {
        getTexture().delete();
    }
}
