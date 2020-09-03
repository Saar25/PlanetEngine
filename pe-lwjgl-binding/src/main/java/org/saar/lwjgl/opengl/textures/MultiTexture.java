package org.saar.lwjgl.opengl.textures;

import java.util.function.Consumer;

public class MultiTexture implements ReadOnlyTexture {

    private final ReadOnlyTexture blendMap; // Blend Map Texture
    private final ReadOnlyTexture dTexture; // Default   Texture
    private final ReadOnlyTexture rTexture; // Red       Texture
    private final ReadOnlyTexture gTexture; // Green     Texture
    private final ReadOnlyTexture bTexture; // Blue      Texture

    public MultiTexture(ReadOnlyTexture blendMap, ReadOnlyTexture dTexture, ReadOnlyTexture rTexture,
                        ReadOnlyTexture gTexture, ReadOnlyTexture bTexture) {
        this.blendMap = blendMap;
        this.dTexture = dTexture;
        this.rTexture = rTexture;
        this.gTexture = gTexture;
        this.bTexture = bTexture;
    }

    @Override
    public void bind(int unit) {
        this.blendMap.bind(unit);
        this.dTexture.bind(unit + 1);
        this.rTexture.bind(unit + 2);
        this.gTexture.bind(unit + 3);
        this.bTexture.bind(unit + 4);
    }

    @Override
    public void bind() {
        this.bind(0);
    }

    @Override
    public void unbind() {
        this.forEach(ReadOnlyTexture::unbind);
    }

    @Override
    public void delete() {
        this.forEach(ReadOnlyTexture::delete);
    }

    private void forEach(Consumer<ReadOnlyTexture> consumer) {
        consumer.accept(this.blendMap);
        consumer.accept(this.dTexture);
        consumer.accept(this.rTexture);
        consumer.accept(this.gTexture);
        consumer.accept(this.bTexture);
    }
}
