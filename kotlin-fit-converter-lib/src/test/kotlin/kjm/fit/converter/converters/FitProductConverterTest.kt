package kjm.fit.converter.converters

import com.garmin.fit.DeviceInfoMesg
import com.garmin.fit.FitMessages
import kjm.fit.converter.out.models.FitProduct
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.InputStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FitProductConverterTest {

    private val conversionService = ConversionService()
    private val fileUnderTest: InputStream? = this.javaClass.classLoader.getResourceAsStream("fitfiles/wahoo-fit-example.fit")
    private var fitMessagesUnderTest = FitMessages()

    @BeforeAll
    fun setup() {
        conversionService.addConverter(FitProductConverter())
        assertNotNull(fileUnderTest)

        fitMessagesUnderTest = FitFileConverter().convert(fileUnderTest!!)
    }

    @Test
    fun canConvert() {
        assertTrue(conversionService.canConvert(DeviceInfoMesg::class.java, FitProduct::class.java))
    }

    @Test
    fun convert() {
        val fitProducts = fitMessagesUnderTest.deviceInfoMesgs.map { conversionService.convert(it, FitProduct::class.java) }
        assertTrue(fitProducts.isNotEmpty())
        assertEquals(173, fitProducts.size)
    }
}