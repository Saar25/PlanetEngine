package org.saar.core.model.vertex;

import org.saar.lwjgl.opengl.objects.Vao;

import java.util.ArrayList;
import java.util.List;

public class VerticesBufferSingleVbo implements VerticesBuffer {

    private final List<Integer> intList = new ArrayList<>();
    private final List<Float> floatList = new ArrayList<>();

    @Override
    public void write(int value) {
        this.intList.add(value);
    }

    @Override
    public void write(float value) {
        this.floatList.add(value);
    }

    @Override
    public Vao vao() {
        final Vao vao = Vao.create();
        return null;
    }
}
