package org.saar.maths.noise;

public class MultipliedNoise2f implements Noise2f {

    private final int multiply;
    private final Noise2f noise2f;

    public MultipliedNoise2f(int multiply, Noise2f noise2f) {
        this.multiply = multiply;
        this.noise2f = noise2f;
    }

    @Override
    public float noise(float x, float y) {
        return this.noise2f.noise(x, y) * this.multiply;
    }
}
