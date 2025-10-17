@file:OptIn(ExperimentalSerializationApi::class)

package kjm.fit.converter

import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class KFitProtobufHandlerTest2 {

    private val kFitProtobufHandler = KFitProtobufHandler2()

    @Test
    fun generateProtobufSchemaProto3() {
        fun assertGeneratedFile(fileName: String) {
            val schema = File("build/generated/ksp/jvm/jvmMain/resources/k2pb/kjm/fit/converter/$fileName.proto")
            val expectedSchema =
                this.javaClass.classLoader.getResourceAsStream("examples/protobuf/k2pb/$fileName.proto")
                    ?.bufferedReader().use { it?.readText() }
            assertEquals(expectedSchema, schema.readText())
        }
        var fileCount = 0
        File("build/generated/ksp/jvm/jvmMain/resources/k2pb/kjm/fit/converter/").listFiles()?.forEach {
            assertGeneratedFile(it.nameWithoutExtension)
            fileCount++
        }
        assertEquals(5, fileCount)
    }

    @Test
    fun convertFitToProtoBufHex() { // Failing but not sure how to help here
        val protoBuf = kFitProtobufHandler.convertFitToProtobufHexString(
            "my-test-file",
            this.javaClass.classLoader.getResourceAsStream("fitfiles/tiny-fit-file.fit")!!
        )
        val expectedProtoBuf =
            this.javaClass.classLoader.getResourceAsStream("examples/protobuf/tiny-fit-file-bytestring.proto")
                ?.bufferedReader().use { it?.readText() }
        assertEquals(expectedProtoBuf, protoBuf)
    }

    @Test
    fun convertFitToProtobufHexBackToFit() {
        val fileUnderTest = this.javaClass.classLoader.getResourceAsStream("fitfiles/tiny-fit-file.fit")
        val copiedFileUnderTest = this.javaClass.classLoader.getResourceAsStream("fitfiles/tiny-fit-file.fit")

        val expectedFitFile = KFitDataClassHandler().convertToDataClass("my-test-file", fileUnderTest!!)

        val protoBuf = kFitProtobufHandler.convertFitToProtobufHexString("my-test-file", copiedFileUnderTest!!)
        val fitDataConversionBack = kFitProtobufHandler.convertProtobufHexToFit(protoBuf)

        assertEquals(expectedFitFile, fitDataConversionBack)
    }
}