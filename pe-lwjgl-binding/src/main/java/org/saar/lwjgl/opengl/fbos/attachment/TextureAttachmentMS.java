package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.fbos.ReadOnlyFbo;
import org.saar.lwjgl.opengl.textures.Texture;
import org.saar.lwjgl.opengl.textures.TextureTarget;
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter;
import org.saar.lwjgl.opengl.textures.parameters.MinFilterParameter;
import org.saar.lwjgl.opengl.textures.settings.TextureAnisotropicFilterSetting;
import org.saar.lwjgl.opengl.textures.settings.TextureMagFilterSetting;
import org.saar.lwjgl.opengl.textures.settings.TextureMinFilterSetting;
import org.saar.lwjgl.opengl.textures.settings.TextureMipMapSetting;

public class TextureAttachmentMS extends AbstractAttachment implements MultisampledAttachment {

    private final Texture texture;
    private final FormatType iFormat;
    private final int samples;

    private TextureAttachmentMS(AttachmentType type, int attachmentIndex, FormatType iFormat, int samples) {
        super(type, attachmentIndex);
        this.texture = Texture.create(TextureTarget.TEXTURE_2D_MULTISAMPLE);
        this.iFormat = iFormat;
        this.samples = samples;

        getTexture().setSettings(TextureTarget.TEXTURE_2D,
                new TextureMipMapSetting(),
                new TextureAnisotropicFilterSetting(4f),
                new TextureMagFilterSetting(MagFilterParameter.LINEAR),
                new TextureMinFilterSetting(MinFilterParameter.NEAREST));
    }

    /**
     * Creates a new colour multisampled texture attachment
     *
     * @return the created depth attachment
     */
    public static TextureAttachmentMS ofColour(int index, int samples) {
        return new TextureAttachmentMS(AttachmentType.COLOUR, index, FormatType.RGBA8, samples);
    }

    /**
     * Creates a new depth multisampled texture attachment
     *
     * @return the created depth attachment
     */
    public static TextureAttachmentMS ofDepth(int samples) {
        return new TextureAttachmentMS(AttachmentType.DEPTH, 0, FormatType.DEPTH_COMPONENT, samples);
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void init(ReadOnlyFbo fbo) {
        getTexture().allocateMultisample(samples, iFormat, fbo.getWidth(), fbo.getHeight());
        getTexture().attachToFbo(getAttachmentPoint(), 0);
    }

    @Override
    public void delete() {
        getTexture().delete();
    }
}
