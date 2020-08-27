package org.saar.core.model.loader;

import org.saar.core.node.Node;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.utils.BufferWriter;

public interface NodeWriter<T extends Node> {

    void writeInstance(T instance, BufferWriter writer);

    Attribute[] instanceAttributes();

    int instanceBytes();

}
