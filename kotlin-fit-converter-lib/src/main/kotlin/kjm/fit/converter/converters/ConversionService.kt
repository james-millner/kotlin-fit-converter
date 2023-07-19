package kjm.fit.converter.converters

import java.lang.reflect.ParameterizedType

/**
 * Spring like conversion service for converting between types. My own implementation of a conversion service setup.
 * @see <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/convert/ConversionService.html">Spring ConversionService</a>
 */
class ConversionService {
    private val converters: MutableSet<Converter<*, *>> = mutableSetOf()

    /**
     * Adds a converter to the conversion service.
     * @param converter The converter to add.
     */
    fun <S, T> addConverter(converter: Converter<S, T>) {
        converters.add(converter)
    }

    /**
     * Converts a source object to a target type.
     * @param source The source object to convert.
     * @param targetType The target type to convert to.
     * @return The converted object.
     */
    fun <S, T> convert(source: S, targetType: Class<T>): T? {
        val sourceClass = source!!::class.java
        val converter = findConverter<S, T>(sourceClass, targetType)
            ?: findConverterInSuperclasses(sourceClass.superclass, targetType)
        return converter?.convert(source)
    }

    /**
     * Determines if there is a converter that can convert a source object to a target type.
     * @param source The source object to convert.
     * @param targetType The target type to convert to.
     * @return The converted object.
     */
    fun <S, T> canConvert(sourceType: Class<S>, targetType: Class<T>): Boolean {
        val converter = findConverter<S, T>(sourceType, targetType)
            ?: findConverterInSuperclasses(sourceType.superclass, targetType)
        return converter != null
    }

    /**
     * Finds a converter that can convert a source object to a target type.
     * @param sourceType The source object to convert.
     * @param targetType The target type to convert to.
     * @return The converted object.
     */
    @Suppress("UNCHECKED_CAST")
    private fun <S, T> findConverter(sourceType: Class<*>, targetType: Class<*>): Converter<S, T>? {
        return converters.firstOrNull { converter ->
            converter::class.java.genericInterfaces.firstOrNull()?.let { type ->
                val typeArguments = (type as ParameterizedType).actualTypeArguments
                val (sourceClass, targetClass) = typeArguments.take(2)
                (sourceClass != null && targetClass != null &&
                        sourceType.isAssignableFrom((sourceClass as? Class<*>)!!) &&
                        targetType.isAssignableFrom((targetClass as? Class<*>)!!)
                    )
            } ?: false
        } as? Converter<S, T>
    }

    /**
     * Finds a converter that can convert a source object to a target type in the source object's superclasses.
     * @param sourceClass The source object to convert.
     * @param targetType The target type to convert to.
     * @return The converted object.
     */
    private fun <S, T> findConverterInSuperclasses(sourceClass: Class<*>, targetType: Class<*>): Converter<S, T>? {
        var currentClass: Class<*>? = sourceClass.superclass
        while (currentClass != null) {
            val converter = findConverter<S, T>(currentClass, targetType)  as? Converter<S, T>
            if (converter != null) {
                return converter
            }
            currentClass = currentClass.superclass
        }
        return null
    }
}