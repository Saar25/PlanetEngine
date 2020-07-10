package org.saar.core.model.data;

import org.saar.lwjgl.opengl.constants.DataType;
import org.saar.lwjgl.opengl.constants.VboUsage;
import org.saar.lwjgl.opengl.objects.Attribute;
import org.saar.lwjgl.opengl.objects.DataBuffer;
import org.saar.lwjgl.opengl.objects.IVbo;

public class FloatModelData implements ModelData {

    private final float[] data;
    private final ModelDataInfo[] dataInfo;

    public FloatModelData(float[] data, ModelDataInfo... dataInfo) {
        this.data = data;
        this.dataInfo = dataInfo;
    }

    @Override
    public IVbo vbo() {
        final DataBuffer dataBuffer = new DataBuffer(VboUsage.STATIC_DRAW);
        dataBuffer.allocateFloat(this.data.length);
        dataBuffer.storeData(0, this.data);
        return dataBuffer;
    }

    @Override
    public Attribute[] attributes(int indexOffset) {
        final Attribute[] attributes = new Attribute[this.dataInfo.length];
        for (int i = 0; i < this.dataInfo.length; i++) {
            attributes[i] = Attribute.of(indexOffset + i, this.dataInfo[i].getComponentCount(),
                    DataType.FLOAT, this.dataInfo[i].isNormalized());
        }
        return attributes;
    }
}
