package kjm.fit.converter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.io.InputStream

class KFitDataClassHandlerTest {

    private val fileUnderTest: InputStream? = this.javaClass.classLoader.getResourceAsStream("fitfiles/wahoo-fit-example.fit")
    private val kFitDataClassHandler = KFitDataClassHandler()

    @Test
    fun canConvert() {
        val fitData = kFitDataClassHandler.convert("my-test-file", true, fileUnderTest!!)
        assertNotNull(fitData)
        assertEquals(18, fitData.events.size)
        assertEquals(3, fitData.productsUsed.size)
        assertEquals(16222, fitData.activityRecords.size)
    }
}