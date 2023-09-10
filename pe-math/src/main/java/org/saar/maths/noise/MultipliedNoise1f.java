package org.saar.maths.noise;

public class MultipliedNoise1f implements Noise1f {

    private final int multiply;
    private final Noise1f noise1f;

    public MultipliedNoise1f(int multiply, Noise1f noise1f) {
        this.multiply = multiply;
        this.noise1f = noise1f;
    }

    @Override
    public float noise(float x) {
        return this.noise1f.noise(x) * this.multiply;
    }
}
