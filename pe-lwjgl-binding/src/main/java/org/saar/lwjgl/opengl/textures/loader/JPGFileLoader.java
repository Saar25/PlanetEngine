package org.saar.lwjgl.opengl.textures.loader;

import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.FormatType;
import org.saar.lwjgl.opengl.textures.TextureInfo;
import org.saar.lwjgl.util.buffer.LwjglByteBuffer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

public class JPGFileLoader implements TextureFileLoader {

    private ByteBuffer loadFile(String file) throws IOException {
        try (final InputStream inputStream = JPGFileLoader.class.getResourceAsStream(file)) {
            Objects.requireNonNull(inputStream);

            final int bytes = inputStream.available();
            final byte[] byteArray = new byte[bytes];
            inputStream.read(byteArray);

            return LwjglByteBuffer.callocate(byteArray.length)
                    .put(byteArray).flip().asByteBuffer();
        }
    }

    @Override
    public TextureInfo load(String file) throws IOException {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            final IntBuffer width = stack.callocInt(1);
            final IntBuffer height = stack.callocInt(1);
            final IntBuffer channels = stack.callocInt(1);

            final ByteBuffer byteBuffer = loadFile(file);
            final ByteBuffer buffer = STBImage.stbi_load_from_memory(
                    byteBuffer, width, height, channels, STBImage.STBI_rgb_alpha);
            buffer.flip();

            return new TextureInfo(width.get(), height.get(), FormatType.RGBA, DataType.BYTE, buffer);
        }
    }
}
