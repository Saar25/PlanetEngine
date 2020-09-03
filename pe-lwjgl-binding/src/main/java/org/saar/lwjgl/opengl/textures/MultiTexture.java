package org.saar.lwjgl.opengl.textures;

import java.util.function.Consumer;

public class MultiTexture implements ReadOnlyTexture {

    private final ReadOnlyTexture blendMap; // Blend Map Texture
    private final ReadOnlyTexture dTexture; // Default   Texture
    private final ReadOnlyTexture rTexture; // Red       Texture
    private final ReadOnlyTexture gTexture; // Green     Texture
    private final ReadOnlyTexture bTexture; // Blue      Texture

    public MultiTexture(ReadOnlyTexture blendMap, ReadOnlyTexture dTexture, ReadOnlyTexture rTexture, ReadOnlyTexture gTexture, ReadOnlyTexture bTexture) {
        this.blendMap = blendMap;
        this.dTexture = dTexture;
        this.rTexture = rTexture;
        this.gTexture = gTexture;
        this.bTexture = bTexture;
    }

    @Override
    public void bind(int unit) {
        blendMap.bind(unit);
        dTexture.bind(unit + 1);
        rTexture.bind(unit + 2);
        gTexture.bind(unit + 3);
        bTexture.bind(unit + 4);
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
        consumer.accept(blendMap);
        consumer.accept(dTexture);
        consumer.accept(rTexture);
        consumer.accept(gTexture);
        consumer.accept(bTexture);
    }
}
