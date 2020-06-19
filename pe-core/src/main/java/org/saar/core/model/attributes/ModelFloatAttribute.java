package org.saar.core.model.attributes;

import org.saar.core.model.ModelAttribute;
import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.IVbo;

import java.util.Arrays;

public class ModelFloatAttribute implements ModelAttribute {

    private final float[] data;
    private final int[] sizes;

    public ModelFloatAttribute(float[] data, int[] sizes) {
        this.data = data;
        this.sizes = sizes;
    }

    @Override
    public int count() {
        final int perVertex = Arrays.stream(this.sizes).sum();
        return this.data.length / perVertex;
    }

    @Override
    public IVbo vbo() {
        final DataBuffer vbo = new DataBuffer(VboUsage.STATIC_DRAW);
        vbo.allocateFloat(this.data.length);
        vbo.storeData(0, this.data);
        return vbo;
    }

    @Override
    public Attribute[] attributes() {
        final Attribute[] attributes = new Attribute[this.sizes.length];
        for (int i = 0; i < this.sizes.length; i++) {
            attributes[i] = Attribute.of(i, this.sizes[i], DataType.FLOAT, false);
        }
        return attributes;
    }
}
