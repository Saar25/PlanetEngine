package org.saar.core.common.obj;

import org.saar.core.node.Node;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.maths.objects.Transform;

public interface IObjNode extends Node {

    Transform getTransform();

    ReadOnlyTexture getTexture();

}
