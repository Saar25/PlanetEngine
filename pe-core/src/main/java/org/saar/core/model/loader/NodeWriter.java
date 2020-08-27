package org.saar.core.model.loader;

import org.saar.core.node.Node;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.primitive.GlPrimitive;

public interface NodeWriter<T extends Node> {

    GlPrimitive[] instanceValues(T node);

    Attribute[] instanceAttributes();

    int instanceBytes();

}
