package org.saar.lwjgl.opengl.fbos.attachment;

import org.saar.lwjgl.opengl.constants.DataType;
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

public class TextureAttachment extends AbstractAttachment implements Attachment {

    private final Texture texture;
    private final FormatType iFormat;
    private final FormatType format;

    private TextureAttachment(AttachmentType type, int attachmentIndex, Texture texture,
                              FormatType iFormat, FormatType format) {
        super(type, attachmentIndex);
        this.texture = texture;
        this.iFormat = iFormat;
        this.format = format;

        getTexture().setSettings(TextureTarget.TEXTURE_2D,
                new TextureMipMapSetting(),
                new TextureAnisotropicFilterSetting(4f),
                new TextureMagFilterSetting(MagFilterParameter.LINEAR),
                new TextureMinFilterSetting(MinFilterParameter.NEAREST));
    }

    public static TextureAttachment ofColour(int index) {
        return new TextureAttachment(AttachmentType.COLOUR, index, Texture.create(),
                FormatType.RGBA8, FormatType.RGBA);
    }

    public static TextureAttachment ofDepth(FormatType depthLevel) {
        return new TextureAttachment(AttachmentType.DEPTH, 0, Texture.create(),
                depthLevel, FormatType.DEPTH_COMPONENT);
    }

    public static TextureAttachment ofStencil() {
        return new TextureAttachment(AttachmentType.STENCIL, 0, Texture.create(),
                FormatType.RGBA8, FormatType.RGBA);
    }

    public static TextureAttachment ofDepthStencil() {
        return new TextureAttachment(AttachmentType.DEPTH_STENCIL, 0, Texture.create(),
                FormatType.RGBA8, FormatType.RGBA);
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void init(ReadOnlyFbo fbo) {
        getTexture().allocate(TextureTarget.TEXTURE_2D, 0, this.iFormat, fbo.getWidth(),
                fbo.getHeight(), 0, this.format, DataType.U_BYTE, null);
        getTexture().attachToFbo(getAttachmentPoint(), 0);
    }

    @Override
    public void delete() {
        getTexture().delete();
    }
}
