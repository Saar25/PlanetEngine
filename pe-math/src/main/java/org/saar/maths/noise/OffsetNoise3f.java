package org.saar.maths.noise;

public class OffsetNoise3f implements Noise3f {

    private final int offset;
    private final Noise3f noise3f;

    public OffsetNoise3f(int offset, Noise3f noise3f) {
        this.offset = offset;
        this.noise3f = noise3f;
    }

    @Override
    public float noise(float x, float y, float z) {
        return this.noise3f.noise(x, y, z) + this.offset;
    }
}
