package org.saar.maths.noise;

public class LayeredNoise3f implements Noise3f {

    private final Noise3f noise3f;
    private final int layers;

    public LayeredNoise3f(Noise3f noise3f, int layers) {
        this.noise3f = noise3f;
        this.layers = layers;
    }

    @Override
    public float noise(float x, float y, float z) {
        float noise = 0f;
        for (int i = 0; i < this.layers; i++) {
            final float pow2 = (float) Math.pow(2, i);
            noise += this.noise3f.noise(x / pow2, y / pow2, z / pow2) * pow2;
        }
        return (float) (noise / (Math.pow(2, this.layers) - 1));
    }
}
