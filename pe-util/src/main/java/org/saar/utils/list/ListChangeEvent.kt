package org.saar.utils.list

data class ListChangeEvent<T>(
        val list: ObservableList<T>,
        val added: List<T>,
        val removed: List<T>)