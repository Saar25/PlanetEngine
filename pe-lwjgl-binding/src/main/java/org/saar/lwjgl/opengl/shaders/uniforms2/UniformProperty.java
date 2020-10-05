package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.jproperty.Property;

public interface UniformProperty<T> extends Uniform, UniformValue<T> {

    Property<? super T> valueProperty();

}
