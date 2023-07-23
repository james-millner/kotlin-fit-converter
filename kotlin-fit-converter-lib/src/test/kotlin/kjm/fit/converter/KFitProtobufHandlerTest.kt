@file:OptIn(ExperimentalSerializationApi::class)

package kjm.fit.converter

import kotlinx.serialization.ExperimentalSerializationApi
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File

class KFitProtobufHandlerTest {

    private val kFitProtobufHandler = KFitProtobufHandler()

    @Test
    fun generateProtobufSchema() {
        val schema = kFitProtobufHandler.getProtoBufSchema()
        val expectedSchema = this.javaClass.classLoader.getResourceAsStream("examples/protobuf/schema/fit-file-data-schema.proto")?.bufferedReader().use { it?.readText() }
        assertEquals(expectedSchema, schema)
    }

    @Test
    fun convertFitToProtoBufHex() {
        val protoBuf = kFitProtobufHandler.convertFitToProtobufHexString("my-test-file", true, this.javaClass.classLoader.getResourceAsStream("fitfiles/tiny-fit-file.fit")!!)
        val expectedProtoBuf = this.javaClass.classLoader.getResourceAsStream("examples/protobuf/tiny-fit-file-bytestring.proto")?.bufferedReader().use { it?.readText() }
        assertEquals(expectedProtoBuf, protoBuf)
    }

    @Test
    fun convertFitToProtobufHexBackToFit() {
        val fileUnderTest = this.javaClass.classLoader.getResourceAsStream("fitfiles/tiny-fit-file.fit")
        val copiedFileUnderTest = this.javaClass.classLoader.getResourceAsStream("fitfiles/tiny-fit-file.fit")

        val expectedFitFile = KFitDataClassHandler().convertToDataClass("my-test-file", true, fileUnderTest!!)

        val protoBuf = kFitProtobufHandler.convertFitToProtobufHexString("my-test-file", true, copiedFileUnderTest!!)
        val fitDataConversionBack = kFitProtobufHandler.convertProtobufHexToFit(protoBuf)

        assertEquals(expectedFitFile, fitDataConversionBack)
    }

    @Test
    fun convertFitToProtoBufByteArray() {
        val protoBuf = kFitProtobufHandler.convertFitToProtobufByteArray("my-test-file", true, this.javaClass.classLoader.getResourceAsStream("fitfiles/tiny-fit-file.fit")!!)
        val expectedProtoBuf = this.javaClass.classLoader.getResourceAsStream("examples/protobuf/tiny-fit-file-bytearray.proto").readBytes()
        assertArrayEquals(expectedProtoBuf, protoBuf)
    }

    //Write a test that writes the protobuf to a file and then reads it back in and converts it back to a FitFileData object
    @Test
    fun convertFitToProtobufByteArrayToFit() {
        val expectedProtoBuf = this.javaClass.classLoader.getResourceAsStream("examples/protobuf/tiny-fit-file-bytearray.proto").readBytes()
        val expectedFitFile = KFitDataClassHandler().convertToDataClass("my-test-file", true, this.javaClass.classLoader.getResourceAsStream("fitfiles/tiny-fit-file.fit")!!)

        val protoBuf = kFitProtobufHandler.convertProtobufByteArrayToFit(expectedProtoBuf)

        assertEquals(expectedFitFile, protoBuf)
    }



}