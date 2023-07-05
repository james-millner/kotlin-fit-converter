package kjm.fit.converter

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.InputStream

class KFitJsonHandlerTest {

    private val fileUnderTest: InputStream? = this.javaClass.classLoader.getResourceAsStream("fitfiles/wahoo-fit-example.fit")
    private val expectedJSON: InputStream? = this.javaClass.classLoader.getResourceAsStream("examples/wahoo-fit-example.json")
    private val kFitJsonHandler = KFitJsonHandler()

    @Test
    fun convertFitToJSON() {
        val jsonString = kFitJsonHandler.convertFitToJSON("my-test-file", true, fileUnderTest!!)
        val expectedJson = expectedJSON?.bufferedReader().use { it?.readText() }
        assertEquals(expectedJson, jsonString)
    }
}