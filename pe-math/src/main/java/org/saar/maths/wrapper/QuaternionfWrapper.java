package org.saar.maths.wrapper;

import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.saar.maths.utils.Quaternion;

public class QuaternionfWrapper {

    private final Quaternionf value = Quaternion.create();
    private final Quaternionf readonly = Quaternion.create();

    public Quaternionf getValue() {
        return this.value;
    }

    public Quaternionfc getReadonly() {
        this.readonly.set(getValue());
        return this.readonly;
    }
}
