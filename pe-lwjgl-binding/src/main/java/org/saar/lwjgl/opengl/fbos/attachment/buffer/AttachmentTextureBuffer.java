package org.saar.lwjgl.opengl.fbos.attachment.buffer;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.IInternalFormat;
import org.saar.lwjgl.opengl.constants.InternalFormat;
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
    private final IInternalFormat iFormat;
    private final FormatType format;
    private final DataType dataType;

    public AttachmentTextureBuffer(Texture texture) {
        this(texture, InternalFormat.RGBA8, FormatType.RGBA, DataType.U_BYTE);
    }

    public AttachmentTextureBuffer(Texture texture, IInternalFormat iFormat, FormatType format, DataType dataType) {
        this.texture = texture;
        this.iFormat = iFormat;
        this.format = format;
        this.dataType = dataType;
    }

    public static AttachmentTextureBuffer create() {
        final Texture texture = Texture.create(TextureTarget.TEXTURE_2D);
        return new AttachmentTextureBuffer(texture);
    }

    public static AttachmentTextureBuffer create(IInternalFormat iFormat, FormatType format, DataType dataType) {
        final Texture texture = Texture.create(TextureTarget.TEXTURE_2D);
        return new AttachmentTextureBuffer(texture, iFormat, format, dataType);
    }

    private void setTextureSettings() {
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
        getTexture().allocate(TextureTarget.TEXTURE_2D, 0, this.iFormat,
                width, height, 0, this.format, this.dataType, null);
        setTextureSettings();
    }

    @Override
    public void allocateMultisample(int width, int height, int samples) {
        getTexture().allocateMultisample(samples, this.iFormat, width, height);
        setTextureSettings();
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
