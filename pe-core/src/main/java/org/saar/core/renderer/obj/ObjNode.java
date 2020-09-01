package org.saar.core.renderer.obj;

import org.saar.core.node.Node;
import org.saar.lwjgl.opengl.textures.ITexture;
import org.saar.maths.objects.Transform;

public interface ObjNode extends Node {

    Transform getTransform();

    ITexture getTexture();

}
