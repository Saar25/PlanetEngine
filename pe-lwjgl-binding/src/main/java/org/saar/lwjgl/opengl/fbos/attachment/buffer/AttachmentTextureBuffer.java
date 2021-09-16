package org.saar.lwjgl.opengl.fbos.attachment.buffer;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.texture.MutableTexture2D;
import org.saar.lwjgl.opengl.texture.parameter.TextureAnisotropicFilterParameter;
import org.saar.lwjgl.opengl.texture.parameter.TextureMagFilterParameter;
import org.saar.lwjgl.opengl.texture.parameter.TextureMinFilterParameter;
import org.saar.lwjgl.opengl.texture.values.MagFilterValue;
import org.saar.lwjgl.opengl.texture.values.MinFilterValue;

public class AttachmentTextureBuffer implements AttachmentBuffer {

    private final MutableTexture2D texture;
    private final InternalFormat iFormat;
    private final FormatType format;
    private final DataType dataType;

    public AttachmentTextureBuffer(MutableTexture2D texture, InternalFormat iFormat) {
        this(texture, iFormat, FormatType.RGBA, DataType.U_BYTE);
    }

    public AttachmentTextureBuffer(MutableTexture2D texture, InternalFormat iFormat, FormatType format, DataType dataType) {
        this.texture = texture;
        this.iFormat = iFormat;
        this.format = format;
        this.dataType = dataType;
        init();
    }

    private void init() {
        this.texture.applyParameters(
                new TextureAnisotropicFilterParameter(4f),
                new TextureMagFilterParameter(MagFilterValue.LINEAR),
                new TextureMinFilterParameter(MinFilterValue.NEAREST));
        this.texture.generateMipmap();
    }

    @Override
    public void allocate(int width, int height) {
        this.texture.allocate(0, this.iFormat, width, height, 0, this.format, this.dataType, null);
    }

    @Override
    public void allocateMultisample(int width, int height, int samples) {
        this.texture.allocateMultisample(samples, this.iFormat, width, height, true);
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
