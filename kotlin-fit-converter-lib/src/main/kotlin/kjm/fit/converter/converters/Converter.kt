package kjm.fit.converter.converters

/**
 * Basic conversion interface for all converters to implement. Again similar structure to Springs own implementation.
 * @param S The source type.
 * @param T The target type.
 * @see <a href="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/convert/converter/Converter.html">Spring Converter</a>
 */
interface Converter<S, T> {
    fun convert(source: S): T
}