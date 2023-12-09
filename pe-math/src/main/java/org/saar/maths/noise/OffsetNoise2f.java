package org.saar.maths.noise;

public class OffsetNoise2f implements Noise2f {

    private final int offset;
    private final Noise2f noise2f;

    public OffsetNoise2f(int offset, Noise2f noise2f) {
        this.offset = offset;
        this.noise2f = noise2f;
    }

    @Override
    public float noise(float x, float y) {
        return this.noise2f.noise(x, y) + this.offset;
    }
}
