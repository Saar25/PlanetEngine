package org.saar.lwjgl.opengl.utils.buffers;

import org.joml.*;

public interface BufferWriter {

    void write(byte value);

    void write(short value);

    void write(char value);

    void write(int value);

    void write(float value);

    void write(double value);

    void write(long value);

    void write(Vector2fc value);

    void write(Vector3fc value);

    void write(Vector4fc value);

    void write(Matrix4fc value);

    void write(Vector2ic value);

    void write(Vector3ic value);

    void write(Vector4ic value);
}
