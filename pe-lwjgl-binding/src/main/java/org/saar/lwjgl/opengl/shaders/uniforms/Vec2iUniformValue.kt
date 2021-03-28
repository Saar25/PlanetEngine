package org.saar.lwjgl.opengl.shaders.uniforms;

import org.joml.Vector2i;
import org.joml.Vector2ic;

public class Vec2iUniformValue extends Vec2iUniform implements UniformValue<Vector2ic> {

    private final String name;

    private final Vector2i value = new Vector2i();

    public Vec2iUniformValue(String name) {
        this.name = name;
    }

    @Override
    public final Vector2ic getUniformValue() {
        return getValue();
    }

    @Override
    public final String getName() {
        return this.name;
    }

    @Override
    public final Vector2i getValue() {
        return this.value;
    }

    @Override
    public final void setValue(Vector2ic value) {
        getValue().set(value);
    }
}
