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

class KFitConverter {

    private var conversionService: ConversionService = ConversionService()

    init {
        conversionService.addConverter(FitDataWrapperConverter())
        conversionService.addConverter(FitEventMesgConverter())
        conversionService.addConverter(FitFileConverter())
        conversionService.addConverter(FitRecordMesgConverter())
        conversionService.addConverter(FitProductConverter())
    }

    fun convert(fileName: String, metricSystem: Boolean, source: InputStream): FitFileData {

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