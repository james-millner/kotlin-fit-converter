package kjm.fit.converter

import com.garmin.fit.FitMessages
import kjm.fit.converter.converters.*
import kjm.fit.converter.out.models.ActivityRecord
import kjm.fit.converter.out.models.FitEvent
import kjm.fit.converter.out.models.FitFileData
import kjm.fit.converter.out.models.FitProduct
import kjm.fit.converter.wrappers.FitDataWrapper
import java.io.IOException
import java.io.InputStream

/**
 * Handler for converting Fit Files into a FitFileData data class.
 * @see FitFileData
 * @see FitDataWrapper
 * @see FitMessages
 * @see FitEvent
 * @see FitProduct
 * @see ActivityRecord
 * @see FitFileConverter
 * @see FitEventMesgConverter
 * @see FitProductConverter
 * @see FitRecordMesgConverter
 * @see FitDataWrapperConverter
 * @see ConversionService
 */
class KFitDataClassHandler {

    private var conversionService: ConversionService = ConversionService()

    init {
        conversionService.addConverter(FitDataWrapperConverter())
        conversionService.addConverter(FitEventMesgConverter())
        conversionService.addConverter(FitFileConverter())
        conversionService.addConverter(FitRecordMesgConverter())
        conversionService.addConverter(FitProductConverter())
    }

    /**
     * Converts a FIT file to a FitFileData data class.
     * @param fileName The name of the file being converted.
     * @param metricSystem Whether to use the metric or imperial system for metrics.
     * @param source The FIT file to convert.
     * @return The FitFileData data class.
     * @throws IOException If the file cannot be converted.
     */
    fun convertToDataClass(fileName: String, metricSystem: Boolean, source: InputStream): FitFileData {

        val fitMessages = conversionService.convert(source, FitMessages::class.java)
            ?: throw IOException("Unable to convert file to FitMessages. Please check the input file location.")

        val session = fitMessages.sessionMesgs.firstOrNull()
        val events =
            fitMessages.eventMesgs?.mapNotNull { conversionService.convert(it, FitEvent::class.java) }?.toSet() ?: emptySet()
        val products = fitMessages.deviceInfoMesgs.mapNotNull { conversionService.convert(it, FitProduct::class.java) }
            .filter { it.productName != "GPS" }.toSet()
        val records = fitMessages.recordMesgs?.mapNotNull { conversionService.convert(it, ActivityRecord::class.java) }?.toSet()
            ?: emptySet()

        val fitDataWrapper = FitDataWrapper(
            fitFileName = fileName,
            metricSystem = if(metricSystem) "metric" else "imperial",
            session = session,
            events = events,
            products = products,
            records = records,
        )

        return conversionService.convert(fitDataWrapper, FitFileData::class.java)
            ?: throw IOException("Unable to convert FitDataWrapper to FitToJson")
    }
}