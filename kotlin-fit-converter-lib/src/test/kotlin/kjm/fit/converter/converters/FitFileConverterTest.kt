package kjm.fit.converter.converters

import com.garmin.fit.FitMessages
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.InputStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FitFileConverterTest {

    private val conversionService = ConversionService()
    private val fileUnderTest: InputStream? = this.javaClass.classLoader.getResourceAsStream("fitfiles/wahoo-fit-example.fit")

    @BeforeAll
    fun setup() {
        conversionService.addConverter(FitFileConverter())
        assertNotNull(fileUnderTest)
    }

    @Test
    fun canConvert() {
        assertTrue(conversionService.canConvert(InputStream::class.java, FitMessages::class.java))
    }

    @Test
    fun convert() {
        val fitMessages = conversionService.convert(fileUnderTest!!, FitMessages::class.java)
        assertNotNull(fitMessages)
        assertEquals(1, fitMessages!!.fileIdMesgs.size)
        assertEquals(1, fitMessages.sportMesgs.size)
        assertEquals(5, fitMessages.hrZoneMesgs.size)
        assertEquals(6, fitMessages.powerZoneMesgs.size)
        assertEquals(16222, fitMessages.recordMesgs.size)
    }
}