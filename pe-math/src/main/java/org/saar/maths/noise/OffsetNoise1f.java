package org.saar.maths.noise;

public class OffsetNoise1f implements Noise1f {

    private final int offset;
    private final Noise1f noise1f;

    public OffsetNoise1f(int offset, Noise1f noise1f) {
        this.offset = offset;
        this.noise1f = noise1f;
    }

    @Override
    public float noise(float x) {
        return this.noise1f.noise(x) + this.offset;
    }
}
