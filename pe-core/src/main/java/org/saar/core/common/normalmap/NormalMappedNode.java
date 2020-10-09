package org.saar.core.common.normalmap;

import org.saar.core.node.Node;
import org.saar.lwjgl.opengl.textures.ReadOnlyTexture;
import org.saar.maths.transform.Transform;

public interface NormalMappedNode extends Node {

    Transform getTransform();

    ReadOnlyTexture getTexture();

    ReadOnlyTexture getNormalMap();

}
