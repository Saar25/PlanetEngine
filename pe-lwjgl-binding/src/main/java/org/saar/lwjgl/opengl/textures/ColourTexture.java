package org.saar.lwjgl.opengl.textures;

import org.lwjgl.system.MemoryUtil;
import org.saar.lwjgl.opengl.utils.MemoryUtils;

import java.nio.ByteBuffer;

public class ColourTexture implements ITexture {

    private final ITexture texture;
    private final int r;
    private final int g;
    private final int b;
    private final int a;

    public ColourTexture(ITexture texture, int r, int g, int b, int a) {
        this.texture = texture;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public static ColourTexture of(int r, int g, int b, int a) {
        final Texture2D texture = new Texture2D(1, 1);
        final ByteBuffer buffer = MemoryUtils.allocByte(4);
        buffer.put((byte) r).put((byte) g).put((byte) b).put((byte) a).flip();
        texture.load(buffer);
        MemoryUtil.memFree(buffer);
        return new ColourTexture(texture, r, g, b, a);
    }

    public int getRed() {
        return this.r;
    }

    public int getGreen() {
        return this.g;
    }

    public int getBlue() {
        return this.b;
    }

    public int getAlpha() {
        return this.a;
    }

    private ITexture getTexture() {
        return this.texture;
    }

    @Override
    public void bind(int unit) {
        getTexture().bind(unit);
    }

    @Override
    public void bind() {
        getTexture().bind();
    }

    @Override
    public void unbind() {
        getTexture().unbind();
    }

    @Override
    public void delete() {
        getTexture().delete();
    }
}
