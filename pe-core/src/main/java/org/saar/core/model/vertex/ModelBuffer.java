package org.saar.core.model.vertex;

import org.saar.lwjgl.opengl.objects.Vao;

public interface ModelBuffer {

    void write(int value);

    void write(float value);

    void writeIndex(int index);

    Vao vao();

}
