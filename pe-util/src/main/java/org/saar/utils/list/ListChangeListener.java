package org.saar.utils.list;

public interface ListChangeListener<T> {

    void changed(ListChangeEvent<T> t);

}
