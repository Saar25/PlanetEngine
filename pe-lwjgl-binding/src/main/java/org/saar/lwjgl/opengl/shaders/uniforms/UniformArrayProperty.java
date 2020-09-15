package org.saar.lwjgl.opengl.shaders.uniforms;

import org.saar.lwjgl.opengl.shaders.InstanceRenderState;
import org.saar.lwjgl.opengl.shaders.ShadersProgram;
import org.saar.lwjgl.opengl.shaders.StageRenderState;

import java.util.ArrayList;
import java.util.List;

public class UniformArrayProperty<T> implements UniformProperty<T> {

    private final String name;
    private final int length;

    private final List<UniformProperty<T>> uniforms;

    public UniformArrayProperty(String name, int length, UniformSupplier<T> supplier) {
        this.name = name;
        this.length = length;
        this.uniforms = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            uniforms.add(supplier.createUniform(name + "[" + i + "]", i));
        }
    }

    @Override
    public void initialize(ShadersProgram shadersProgram) {
        for (UniformProperty<T> uniform : uniforms) {
            uniform.initialize(shadersProgram);
        }
    }

    @Override
    public void loadOnStage(StageRenderState state) {
        for (int i = 0; i < length; i++) {
            uniforms.get(i).loadOnStage(state);
        }
    }

    @Override
    public void loadOnInstance(InstanceRenderState<T> state) {
        for (int i = 0; i < length; i++) {
            uniforms.get(i).loadOnInstance(state);
        }
    }

    public String getName() {
        return name;
    }

    public interface UniformSupplier<T> {
        UniformProperty<T> createUniform(String name, int index);
    }
}
