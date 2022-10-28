package org.saar.maths.noise;

public class LayeredNoise2f implements Noise2f {

    private final Noise2f noise2f;
    private final int layers;

    public LayeredNoise2f(Noise2f noise2f, int layers) {
        this.noise2f = noise2f;
        this.layers = layers;
    }

    @Override
    public float noise(float x, float y) {
        float noise = 0f;
        for (int i = 0; i < this.layers; i++) {
            final float pow2 = (float) Math.pow(2, i);
            noise += this.noise2f.noise(x / pow2, y / pow2) * pow2;
        }
        return (float) (noise / (Math.pow(2, this.layers) - 1));
    }
}
