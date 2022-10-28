package org.saar.maths.noise;

public class LayeredNoise1f implements Noise1f {

    private final Noise1f noise1f;
    private final int layers;

    public LayeredNoise1f(Noise1f noise1f, int layers) {
        this.noise1f = noise1f;
        this.layers = layers;
    }

    @Override
    public float noise(float x) {
        float noise = 0f;
        for (int i = 0; i < this.layers; i++) {
            final float pow2 = (float) Math.pow(2, i);
            noise += this.noise1f.noise(x / pow2) * pow2;
        }
        return (float) (noise / (Math.pow(2, this.layers) - 1));
    }
}
