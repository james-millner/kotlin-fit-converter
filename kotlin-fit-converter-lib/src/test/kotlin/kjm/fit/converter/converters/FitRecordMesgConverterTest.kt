package kjm.fit.converter.converters

import com.garmin.fit.FitMessages
import com.garmin.fit.RecordMesg
import kjm.fit.converter.out.models.LocationRecord
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.InputStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FitRecordMesgConverterTest {

    private val conversionService = ConversionService()
    private val fileUnderTest: InputStream? = this.javaClass.classLoader.getResourceAsStream("fitfiles/wahoo-fit-example.fit")
    private var fitMessagesUnderTest = FitMessages()

    @BeforeAll
    fun setup() {
        conversionService.addConverter(FitLocationDataConverter())
        assertNotNull(fileUnderTest)

        fitMessagesUnderTest = FitFileConverter().convert(fileUnderTest!!)
    }

    @Test
    fun canConvert() {
        assertTrue(conversionService.canConvert(RecordMesg::class.java, LocationRecord::class.java))
    }

    @Test
    fun convert() {
        val activityRecords = fitMessagesUnderTest.recordMesgs.map { conversionService.convert(it, LocationRecord::class.java) }
        assertTrue(activityRecords.isNotEmpty())
        assertEquals(16222, activityRecords.size)
    }
}