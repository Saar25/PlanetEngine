package org.saar.core.model.lod;

public class LevelOfDetail {

    private final int min;
    private final int max;
    private int current;

    public LevelOfDetail(int min, int max) {
        this.min = min;
        this.max = max;
    }

    private void checkRange() {
        this.current = Math.max(this.current, this.min);
        this.current = Math.min(this.current, this.max);
    }

    public void set(int current) {
        this.current = current;
        checkRange();
    }

    public void inc() {
        this.current++;
        checkRange();
    }

    public void dec() {
        this.current--;
        checkRange();
    }

    public int get() {
        return this.current;
    }
}
