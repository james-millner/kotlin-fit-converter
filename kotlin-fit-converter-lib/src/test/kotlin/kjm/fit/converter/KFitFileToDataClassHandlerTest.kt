package kjm.fit.converter

import kjm.fit.converter.file.KFitFileToDataClassHandler
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.io.InputStream

class KFitFileToDataClassHandlerTest {

    private val fileUnderTest: InputStream? = this.javaClass.classLoader.getResourceAsStream("fitfiles/wahoo-fit-example.fit")
    private val kFitFileToDataClassHandler = KFitFileToDataClassHandler()

    @Test
    fun canConvert() {
        val fitData = kFitFileToDataClassHandler.convert("my-test-file", true, fileUnderTest!!)
        assertNotNull(fitData)
        assertEquals(18, fitData.events.size)
        assertEquals(3, fitData.productsUsed.size)
        assertEquals(16222, fitData.activityRecords.size)
    }
}