package org.saar.core.model.vertex;

import org.saar.lwjgl.opengl.objects.Vao;

public interface VerticesBuffer {

    void write(int value);

    void write(float value);

    Vao vao();

}
