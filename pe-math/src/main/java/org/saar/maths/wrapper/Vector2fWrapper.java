package org.saar.maths.wrapper;

import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.saar.maths.utils.Vector2;

public class Vector2fWrapper {

    private final Vector2f value = Vector2.create();
    private final Vector2f readonly = Vector2.create();

    public Vector2f getValue() {
        return this.value;
    }

    public Vector2fc getReadonly() {
        this.readonly.set(getValue());
        return this.readonly;
    }
}
