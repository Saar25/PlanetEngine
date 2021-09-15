package org.saar.lwjgl.opengl.fbos.attachment.buffer;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.textures.ITexture;
import org.saar.lwjgl.opengl.textures.TextureTarget;
import org.saar.lwjgl.opengl.textures.parameters.MagFilterParameter;
import org.saar.lwjgl.opengl.textures.parameters.MinFilterParameter;
import org.saar.lwjgl.opengl.textures.settings.TextureAnisotropicFilterSetting;
import org.saar.lwjgl.opengl.textures.settings.TextureMagFilterSetting;
import org.saar.lwjgl.opengl.textures.settings.TextureMinFilterSetting;
import org.saar.lwjgl.opengl.textures.settings.TextureMipMapSetting;

public class AttachmentTextureBuffer implements AttachmentBuffer {

    private final ITexture texture;
    private final InternalFormat iFormat;
    private final FormatType format;
    private final DataType dataType;

    public AttachmentTextureBuffer(ITexture texture, InternalFormat iFormat) {
        this(texture, iFormat, FormatType.RGBA, DataType.U_BYTE);
    }

    public AttachmentTextureBuffer(ITexture texture, InternalFormat iFormat, FormatType format, DataType dataType) {
        this.texture = texture;
        this.iFormat = iFormat;
        this.format = format;
        this.dataType = dataType;
    }

    private void setTextureSettings() {
        this.texture.setSettings(TextureTarget.TEXTURE_2D,
                new TextureMipMapSetting(),
                new TextureAnisotropicFilterSetting(4f),
                new TextureMagFilterSetting(MagFilterParameter.LINEAR),
                new TextureMinFilterSetting(MinFilterParameter.NEAREST));
    }

    @Override
    public void allocate(int width, int height) {
        this.texture.allocate(TextureTarget.TEXTURE_2D, 0, this.iFormat,
                width, height, 0, this.format, this.dataType, null);
        setTextureSettings();
    }

    @Override
    public void allocateMultisample(int width, int height, int samples) {
        this.texture.allocateMultisample(TextureTarget.TEXTURE_2D,
                samples, this.iFormat, width, height, true);
        setTextureSettings();
    }

    @Override
    public void attachToFbo(int attachment) {
        this.texture.attachToFbo(attachment, 0);
    }

    @Override
    public void delete() {
        this.texture.delete();
    }
}
