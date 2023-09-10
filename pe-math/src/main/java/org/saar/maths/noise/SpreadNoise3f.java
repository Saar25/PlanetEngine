package org.saar.maths.noise;

public class SpreadNoise3f implements Noise3f {

    private final int division;
    private final Noise3f noise3f;

    public SpreadNoise3f(int division, Noise3f noise3f) {
        this.division = division;
        this.noise3f = noise3f;
    }

    @Override
    public float noise(float x, float y, float z) {
        return this.noise3f.noise(x / this.division, y / this.division, z / this.division);
    }
}
