package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.jproperty.Property;

public interface UniformProperty<T> extends Uniform {

    Property<? super T> valueProperty();

}
