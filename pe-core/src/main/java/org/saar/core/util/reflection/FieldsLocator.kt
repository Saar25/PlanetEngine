package org.saar.core.util.reflection

class FieldsLocator(private val obj: Any) {

    fun <T> getFilteredValues(tClass: Class<T>, annotation: Class<out Annotation>): List<T> {
        return obj.javaClass.declaredFields
            .filter { tClass.isAssignableFrom(it.type) && it.isAnnotationPresent(annotation) }
            .onEach { it.isAccessible = true }
            .map { tClass.cast(it.get(this.obj)) }
    }
}
