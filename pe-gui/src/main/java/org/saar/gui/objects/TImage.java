package org.saar.gui.objects;

import org.saar.gui.UIObject;
import org.saar.lwjgl.opengl.textures.Texture2D;

public class TImage extends UIObject {

    public TImage(Texture2D texture) {
        super(texture);
        getStyle().backgroundColour.set(0, 0, 0, 0);
    }
}
