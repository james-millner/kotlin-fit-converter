package kjm.fit.converter.converters

import com.garmin.fit.FitMessages
import kjm.fit.converter.out.models.LocationRecord
import kjm.fit.converter.out.models.FitEvent
import kjm.fit.converter.out.models.FitFileData
import kjm.fit.converter.out.models.FitProduct
import kjm.fit.converter.utils.MeasurementUnit
import kjm.fit.converter.wrappers.FitDataWrapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.IOException
import java.io.InputStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FitDataWrapperConverterTest {
    private val conversionService = ConversionService()
    private val fileUnderTest: InputStream? = this.javaClass.classLoader.getResourceAsStream("fitfiles/wahoo-fit-example.fit")
    private lateinit var fitDataWrapper: FitDataWrapper

    @BeforeAll
    fun setup() {
        conversionService.addConverter(FitFileConverter())
        conversionService.addConverter(FitDataWrapperConverter())
        conversionService.addConverter(FitEventConverter())
        conversionService.addConverter(FitDeviceInfoConverter())
        conversionService.addConverter(FitLocationDataConverter())

        assertNotNull(fileUnderTest)

        val fitMessages = conversionService.convert(fileUnderTest, FitMessages::class.java)
            ?: throw IOException("Unable to convert file to FitMessages")

        val session = fitMessages.sessionMesgs.firstOrNull() ?: throw IOException("No session message found in file")
        val events =
            fitMessages.eventMesgs?.mapNotNull { conversionService.convert(it, FitEvent::class.java) }?.toSet() ?: emptySet()
        val products = fitMessages.deviceInfoMesgs.mapNotNull { conversionService.convert(it, FitProduct::class.java) }
            .filter { it.productName != "GPS" }.toSet()
        val records = fitMessages.recordMesgs?.mapNotNull { conversionService.convert(it, LocationRecord::class.java) }?.toSet()
            ?: emptySet()

        fitDataWrapper = FitDataWrapper(
            fitFileName = "FileName",
            metricSystem = MeasurementUnit.METRIC,
            session = session,
            events = events,
            products = products,
            records = records,
        )
    }

    @Test
    fun canConvert() {
        assertTrue(conversionService.canConvert(FitDataWrapper::class.java, FitFileData::class.java))
    }

    @Test
    fun convert() {
        val fitFileData = conversionService.convert(fitDataWrapper, FitFileData::class.java)
        assertNotNull(fitFileData)
        assertEquals("FileName", fitFileData!!.activityName)
        assertEquals("CYCLING", fitFileData.sport)
        assertEquals(3550.0, fitFileData.averageCalories)
        assertEquals(8.6, fitFileData.averageSpeed)
    }
}