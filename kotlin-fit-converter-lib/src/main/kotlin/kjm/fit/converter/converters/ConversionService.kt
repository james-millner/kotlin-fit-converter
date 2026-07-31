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
     * @return The converted object, or null if no registered converter handles the pair.
     */
    fun <S, T> convert(source: S, targetType: Class<T>): T? {
        if (source == null) return null
        val converter = findConverter<S, T>(source!!::class.java, targetType)
        return converter?.convert(source)
    }

    /**
     * Determines if there is a converter that can convert a source type to a target type.
     * @param sourceType The type of the object to convert.
     * @param targetType The target type to convert to.
     * @return True if a registered converter accepts the source type and produces the target type.
     */
    fun <S, T> canConvert(sourceType: Class<S>, targetType: Class<T>): Boolean =
        findConverter<S, T>(sourceType, targetType) != null

    /**
     * Finds a converter that accepts the given source type and produces the given target type.
     * A converter matches when it declares a source type the given type can be assigned to (so subclasses
     * of a converter's source type are handled), and a target type assignable to the requested target.
     * @param sourceType The type of the object to convert.
     * @param targetType The target type to convert to.
     * @return The matching converter, or null if none is registered.
     */
    @Suppress("UNCHECKED_CAST")
    private fun <S, T> findConverter(sourceType: Class<*>, targetType: Class<*>): Converter<S, T>? =
        converters.firstOrNull { converter ->
            val declaredTypes = converter::class.java.genericInterfaces
                .filterIsInstance<ParameterizedType>()
                .firstOrNull { it.rawType == Converter::class.java }
                ?.actualTypeArguments

            val declaredSource = declaredTypes?.getOrNull(0) as? Class<*>
            val declaredTarget = declaredTypes?.getOrNull(1) as? Class<*>

            declaredSource != null && declaredTarget != null &&
                declaredSource.isAssignableFrom(sourceType) &&
                targetType.isAssignableFrom(declaredTarget)
        } as? Converter<S, T>
}