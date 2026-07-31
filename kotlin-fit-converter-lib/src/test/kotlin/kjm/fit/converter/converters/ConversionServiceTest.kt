package kjm.fit.converter.converters

import com.garmin.fit.FitMessages
import kjm.fit.converter.out.models.FitEvent
import kjm.fit.converter.out.models.FitFileData
import kjm.fit.converter.wrappers.FitDataWrapper
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class ConversionServiceTest {

    @Test
    fun canConvert() {
        val conversionService = ConversionService()
        conversionService.addConverter(FitDataWrapperConverter())

        assertTrue(conversionService.canConvert(FitDataWrapper::class.java, FitFileData::class.java))
    }

    @Test
    fun cantConvert() {
        val conversionService = ConversionService()
        conversionService.addConverter(FitDataWrapperConverter())

        assertFalse(conversionService.canConvert(FitDataWrapper::class.java, String::class.java))
    }

    @Test
    fun canConvertSubclassesOfAConvertersSourceType() {
        val conversionService = ConversionService()
        conversionService.addConverter(FitFileConverter())

        assertTrue(conversionService.canConvert(InputStream::class.java, FitMessages::class.java))
        assertTrue(conversionService.canConvert(FileInputStream::class.java, FitMessages::class.java))
        assertTrue(conversionService.canConvert(BufferedInputStream::class.java, FitMessages::class.java))
    }

    @Test
    fun cantConvertUnrelatedSourceTypeToARegisteredTarget() {
        val conversionService = ConversionService()
        conversionService.addConverter(FitFileConverter())
        conversionService.addConverter(FitEventConverter())

        // An InputStream is not an EventMesg, so no converter should be offered for the pair.
        assertFalse(conversionService.canConvert(FileInputStream::class.java, FitEvent::class.java))
    }

    @Test
    fun convertReturnsNullRatherThanThrowingForAnUnhandledPair() {
        val conversionService = ConversionService()
        conversionService.addConverter(FitFileConverter())
        conversionService.addConverter(FitEventConverter())

        File("src/test/resources/fitfiles/tiny-fit-file.fit").inputStream().use { stream ->
            assertNull(conversionService.convert(stream, FitEvent::class.java))
        }
    }

    @Test
    fun convertResolvesAConverterForASubclassOfItsSourceType() {
        val conversionService = ConversionService()
        conversionService.addConverter(FitFileConverter())

        File("src/test/resources/fitfiles/tiny-fit-file.fit").inputStream().use { stream ->
            assertNotNull(conversionService.convert(stream, FitMessages::class.java))
        }
    }
}