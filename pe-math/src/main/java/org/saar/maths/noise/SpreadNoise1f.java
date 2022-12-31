package org.saar.maths.noise;

public class SpreadNoise1f implements Noise1f {

    private final int division;
    private final Noise1f noise1f;

    public SpreadNoise1f(int division, Noise1f noise1f) {
        this.division = division;
        this.noise1f = noise1f;
    }

    @Override
    public float noise(float x) {
        return this.noise1f.noise(x / this.division);
    }
}
