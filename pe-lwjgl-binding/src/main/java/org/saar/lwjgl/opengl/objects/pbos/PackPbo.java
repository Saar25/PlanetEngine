package org.saar.lwjgl.opengl.objects.pbos;

import org.lwjgl.opengl.GL21;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.IFormatType;
import org.saar.lwjgl.opengl.constants.VboTarget;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.vbos.Vbo;

import java.nio.ByteBuffer;

public class PackPbo implements ReadablePbo {

    private final Vbo buffer;

    private PackPbo(Vbo buffer) {
        this.buffer = buffer;
    }

    public static PackPbo create() {
        final Vbo buffer = Vbo.create(
                VboTarget.PACK_BUFFER,
                VboUsage.STATIC_DRAW);
        return new PackPbo(buffer);
    }

    @Override
    public void readPixels(int x, int y, int width, int height, IFormatType format,
                           DataType dataType, ByteBuffer pixels) {
        this.buffer.bind();
        GL21.glReadPixels(x, y, width, height, format.get(), dataType.get(), pixels);
    }

    @Override
    public void delete() {
        this.buffer.delete();
    }
}
