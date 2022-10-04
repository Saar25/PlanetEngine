package org.saar.maths.noise;

public class SpreadNoise2f implements Noise2f {

    private final int division;
    private final Noise2f noise2f;

    public SpreadNoise2f(int division, Noise2f noise2f) {
        this.division = division;
        this.noise2f = noise2f;
    }

    @Override
    public float noise(float x, float y) {
        return this.noise2f.noise(x / this.division, y / this.division);
    }
}
