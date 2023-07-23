package kjm.fit.converter

import kjm.fit.converter.utils.MeasurementUnit
import kjm.fit.converter.utils.MeasurementUtils
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.io.InputStream

class KFitDataClassHandlerTest {

    private val fileUnderTest: InputStream? = this.javaClass.classLoader.getResourceAsStream("fitfiles/wahoo-fit-example.fit")
    private val kFitDataClassHandler = KFitDataClassHandler()
    private val measurementUtils = MeasurementUtils()

    @Test
    fun canConvert() {
        val fitData = kFitDataClassHandler.convertToDataClass("my-test-file", true, fileUnderTest!!)
        assertNotNull(fitData)
        assertEquals(20, fitData.events.size)
        assertEquals(38.35, fitData.totalDistance)
        assertEquals(3, fitData.productsUsed.size)
        assertEquals(16222, fitData.locationRecords.size)
        assertEquals(fitData.totalDistance, measurementUtils.distanceInRequestedUnit(fitData.locationRecords.last().distance!!.toFloat(), MeasurementUnit.METRIC))
    }
}