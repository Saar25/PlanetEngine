package org.saar.lwjgl.opengl.shaders.uniforms2;

import org.saar.utils.property.Property;

public interface UniformProperty<T> extends Uniform {

    Property<? super T> valueProperty();

}
