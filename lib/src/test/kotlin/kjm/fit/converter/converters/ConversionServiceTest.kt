package kjm.fit.converter.converters

import kjm.fit.converter.out.models.FitFileData
import kjm.fit.converter.wrappers.FitDataWrapper
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

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
}