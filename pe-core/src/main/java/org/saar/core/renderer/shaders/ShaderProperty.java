package org.saar.core.renderer.shaders;

import org.saar.lwjgl.opengl.shaders.ShaderType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ShaderProperty {

    ShaderType value();

}
