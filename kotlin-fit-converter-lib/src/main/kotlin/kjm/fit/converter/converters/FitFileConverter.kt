package kjm.fit.converter.converters

import com.garmin.fit.FitDecoder
import com.garmin.fit.FitMessages
import java.io.InputStream

/**
 * Converter for converting InputStream to FitMessages.
 * @see <a href="https://developer.garmin.com/fit/protocol/#file-types">File Types</a>
 * @see FitMessages
 */
internal class FitFileConverter: Converter<InputStream, FitMessages> {
    override fun convert(source: InputStream): FitMessages =
        FitDecoder().decode(source)
}