package kjm.fit.converter.converters

import com.garmin.fit.FitMessages
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.InputStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FitFileConverterTest {

    val conversionService = ConversionService()
    val fileUnderTest = this.javaClass.classLoader.getResourceAsStream("fitfiles/wahoo-fit-example.fit")

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
    }

}