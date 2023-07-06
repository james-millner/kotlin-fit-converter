package kjm.fit.converter.converters

import com.garmin.fit.EventMesg
import com.garmin.fit.FitMessages
import kjm.fit.converter.out.models.FitEvent
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.InputStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FitEventMesgConverterTest {

    private val conversionService = ConversionService()
    private val fileUnderTest: InputStream? = this.javaClass.classLoader.getResourceAsStream("fitfiles/wahoo-fit-example.fit")
    private var fitMessagesUnderTest = FitMessages()

    @BeforeAll
    fun setup() {
        conversionService.addConverter(FitEventMesgConverter())
        assertNotNull(fileUnderTest)

        fitMessagesUnderTest = FitFileConverter().convert(fileUnderTest!!)
    }

    @Test
    fun canConvert() {
        assertTrue(conversionService.canConvert(EventMesg::class.java, FitEvent::class.java))
    }

    @Test
    fun convert() {
        val eventMesg = fitMessagesUnderTest.eventMesgs.map { conversionService.convert(it, FitEvent::class.java) }
        assertTrue(eventMesg.isNotEmpty())
    }
}