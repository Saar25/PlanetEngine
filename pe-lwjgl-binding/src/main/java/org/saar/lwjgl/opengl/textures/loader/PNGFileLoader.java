package org.saar.lwjgl.opengl.textures.loader;

import org.lwjgl.system.MemoryUtil;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.textures.TextureInfo;
import org.saar.utils.file.PNGDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class PNGFileLoader implements TextureFileLoader {

    @Override
    public TextureInfo load(String file) throws IOException {
        final InputStream inputStream = PNGFileLoader.class.getResourceAsStream(file);
        final PNGDecoder decoder = new PNGDecoder(inputStream);

        final ByteBuffer buffer = MemoryUtil.memAlloc(4 * decoder.getWidth() * decoder.getHeight());
        decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
        buffer.flip();

        return new TextureInfo(decoder.getWidth(), decoder.getHeight(),
                FormatType.RGBA, DataType.U_BYTE, buffer);
    }
}
