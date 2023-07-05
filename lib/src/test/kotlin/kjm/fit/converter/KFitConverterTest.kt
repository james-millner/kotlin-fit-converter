package kjm.fit.converter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.io.InputStream

class KFitConverterTest {

    private val fileUnderTest: InputStream? = this.javaClass.classLoader.getResourceAsStream("fitfiles/wahoo-fit-example.fit")
    private val kFitConverter = KFitConverter()


    @Test
    fun canConvert() {
        val fitData = kFitConverter.convert("my-test-file", true, fileUnderTest!!)
        assertNotNull(fitData)
        assertEquals(18, fitData.events.size)
        assertEquals(4, fitData.productsUsed.size)
        assertEquals(16222, fitData.activityRecords.size)
    }
}