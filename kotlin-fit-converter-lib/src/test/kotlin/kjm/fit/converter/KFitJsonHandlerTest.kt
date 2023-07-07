package kjm.fit.converter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KFitJsonHandlerTest {

    private val kFitJsonHandler = KFitJsonHandler()

    @Test
    fun convertFitToJSON() {
        val jsonString = kFitJsonHandler.convertFitToJSON("my-test-file", true, this.javaClass.classLoader.getResourceAsStream("fitfiles/tiny-fit-file.fit")!!)
        val expectedJson = this.javaClass.classLoader.getResourceAsStream("examples/json/tiny-fit-file.json")?.bufferedReader().use { it?.readText() }
        assertEquals(expectedJson, jsonString)
    }

    @Test
    fun convertFitToJSONBackToFit() {
        val fileUnderTest = this.javaClass.classLoader.getResourceAsStream("fitfiles/tiny-fit-file.fit")
        val copiedFileUnderTest = this.javaClass.classLoader.getResourceAsStream("fitfiles/tiny-fit-file.fit")

        val expectedFitFile = KFitDataClassHandler().convert("my-test-file", true, fileUnderTest!!)

        val jsonString = kFitJsonHandler.convertFitToJSON("my-test-file", true, copiedFileUnderTest!!)
        val fitDataConversionBack = kFitJsonHandler.convertJSONToFitData(jsonString)

        assertEquals(expectedFitFile, fitDataConversionBack)
    }
}