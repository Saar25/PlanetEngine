package org.saar.lwjgl.opengl.fbos.attachment.buffer;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.constants.InternalFormat;
import org.saar.lwjgl.opengl.fbos.attachment.index.AttachmentIndex;
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
        if (this.texture.getWidth() > 0 && this.texture.getHeight() > 0) {
            this.texture.generateMipmap();
        }
    }

    @Override
    public void allocate(int width, int height) {
        throw new UnsupportedOperationException("Deprecated");
    }

    @Override
    public void attachToFbo(AttachmentIndex index) {
        this.texture.attachToFbo(index.getValue(), 0);
    }

    @Override
    public void delete() {
        this.texture.delete();
    }
}
