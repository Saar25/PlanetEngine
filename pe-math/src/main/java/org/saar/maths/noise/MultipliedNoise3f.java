package org.saar.maths.noise;

public class MultipliedNoise3f implements Noise3f {

    private final int multiply;
    private final Noise3f noise3f;

    public MultipliedNoise3f(int multiply, Noise3f noise3f) {
        this.multiply = multiply;
        this.noise3f = noise3f;
    }

    @Override
    public float noise(float x, float y, float z) {
        return this.noise3f.noise(x, y, z) * this.multiply;
    }
}
