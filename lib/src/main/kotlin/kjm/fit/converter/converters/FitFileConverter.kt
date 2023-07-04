package kjm.fit.converter.converters

import com.garmin.fit.FitDecoder
import com.garmin.fit.FitMessages
import java.io.InputStream

class FitFileConverter: Converter<InputStream, FitMessages> {
    override fun convert(source: InputStream): FitMessages =
        FitDecoder().decode(source)
}