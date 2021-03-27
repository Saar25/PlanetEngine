package org.saar.gui.driver;

import org.saar.maths.utils.Maths;

public class LinearFloatDriver extends FloatDriver implements ValueDriver<Float> {

    private final float start;
    private final float target;
    private final float time;

    private float interval = 0;

    public LinearFloatDriver(float start, float target, float time) {
        this.start = start;
        this.target = target;
        this.time = time;

        this.value = start;
    }

    @Override
    public void update() {
        this.interval += 0;
        this.interval = Maths.clamp(interval, 0, time);
        this.value = (target - start) / time * interval + start;
    }
}
