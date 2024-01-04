package kjm.fit.converter

import com.garmin.fit.FitMessages
import kjm.fit.converter.converters.*
import kjm.fit.converter.out.models.FitEvent
import kjm.fit.converter.out.models.FitFileData
import kjm.fit.converter.out.models.FitProduct
import kjm.fit.converter.out.models.LocationRecord
import kjm.fit.converter.utils.measurements.MeasurementUnit
import kjm.fit.converter.wrappers.FitDataWrapper
import java.io.IOException
import java.io.InputStream

/**
 * Handler for converting Fit Files into a FitFileData data class.
 * @constructor Creates a new instance of KFitDataClassHandler.
 * @see FitFileData
 * @see FitDataWrapper
 * @see FitMessages
 * @see FitEvent
 * @see FitProduct
 * @see LocationRecord
 * @see FitFileConverter
 * @see FitEventConverter
 * @see FitDeviceInfoConverter
 * @see FitLocationDataConverter
 * @see FitDataWrapperConverter
 * @see ConversionService
 */
class KFitDataClassHandler {

    private var conversionService: ConversionService = ConversionService()

    init {
        conversionService.addConverter(FitDataWrapperConverter())
        conversionService.addConverter(FitEventConverter())
        conversionService.addConverter(FitFileConverter())
        conversionService.addConverter(FitLocationDataConverter())
        conversionService.addConverter(FitDeviceInfoConverter())
    }

    /**
     * Converts a FIT file to a FitFileData data class.
     * @param fileName The name of the file being converted.
     * @param metricSystem Whether to use the metric or imperial system for metrics. MeasurementUtils is used to determine this.
     * @param source The FIT file to convert as an InputStream.
     * @return The FitFileData data class.
     * @see FitFileData
     * @throws IOException If the file cannot be converted.
     */
    fun convertToDataClass(fileName: String, metricSystem: Boolean, source: InputStream): FitFileData {

        val fitMessages = conversionService.convert(source, FitMessages::class.java)
            ?: throw IOException("Unable to convert file to FitMessages. Please check the input file location.")

        val session = fitMessages.sessionMesgs.first() ?: throw Exception("No session message found in file.")
        val events =
            fitMessages.eventMesgs?.mapNotNull { conversionService.convert(it, FitEvent::class.java) }?.toSet() ?: emptySet()
        val products = fitMessages.deviceInfoMesgs.mapNotNull { conversionService.convert(it, FitProduct::class.java) }
            .filter { it.productName != "GPS" }.toSet()
        val records = fitMessages.recordMesgs?.mapNotNull { conversionService.convert(it, LocationRecord::class.java) }?.toSet()
            ?: emptySet()

        val fitDataWrapper = FitDataWrapper(
            fitFileName = fileName,
            metricSystem = if(metricSystem) MeasurementUnit.METRIC else MeasurementUnit.IMPERIAL,
            session = session,
            events = events,
            products = products,
            records = records,
        )

        return conversionService.convert(fitDataWrapper, FitFileData::class.java)
            ?: throw IOException("Unable to convert FitDataWrapper to FitToJson")
    }
}