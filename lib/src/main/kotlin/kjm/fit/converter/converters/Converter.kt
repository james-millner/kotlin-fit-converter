package kjm.fit.converter.converters

interface Converter<S, T> {
    fun convert(source: S): T
}