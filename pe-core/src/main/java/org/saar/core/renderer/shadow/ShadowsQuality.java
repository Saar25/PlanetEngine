package org.saar.core.renderer.shadow;

public class ShadowsQuality {

    public static final ShadowsQuality VERY_LOW = new ShadowsQuality(512); // Level 9

    public static final ShadowsQuality LOW = new ShadowsQuality(1024); // Level 10

    public static final ShadowsQuality MEDIUM = new ShadowsQuality(2048); // Level 11

    public static final ShadowsQuality HIGH = new ShadowsQuality(4096); // Level 12

    public static final ShadowsQuality VERY_HIGH = new ShadowsQuality(8192); // Level 13

    private final int imageSize;

    private ShadowsQuality(int imageSize) {
        this.imageSize = imageSize;
    }

    public static ShadowsQuality ofLevel(int level) {
        final int imageSize = (int) Math.pow(2, level);
        return new ShadowsQuality(imageSize);
    }

    public int getImageSize() {
        return this.imageSize;
    }
}
