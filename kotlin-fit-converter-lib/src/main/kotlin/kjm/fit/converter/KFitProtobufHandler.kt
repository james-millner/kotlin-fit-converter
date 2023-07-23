package kjm.fit.converter

import kjm.fit.converter.out.models.FitFileData
import kjm.fit.converter.out.models.TestClass
import kjm.fit.converter.utils.proto.ProtoBufSchemaGenerator
import kjm.fit.converter.utils.proto.generateProtoBufSchema
import kotlinx.serialization.*
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream

/**
 * Handles the conversion of FIT files to Protobuf and vice versa.
 * @constructor Creates a new instance of KFitProtobufHandler.
 * @property kFitDataClassHandler The KFitDataClassHandler instance to use.
 * @see KFitDataClassHandler
 * @see FitFileData
 * @see ProtoBuf
 * @see kotlinx.serialization
 * @see kotlinx.serialization.protobuf
 */
@ExperimentalSerializationApi
class KFitProtobufHandler {

    private val kFitDataClassHandler = KFitDataClassHandler()

    /**
     * Converts a FIT file to a Protobuf hex string.
     * @param fileName The name of the file being converted.
     * @param metricSystem Whether to use the metric or imperial system for metrics. MeasurementUtils is used to determine this.
     * @param source The FIT file to convert as an InputStream.
     * @return The Protobuf hex string.
     */
    fun convertFitToProtobufHexString(fileName: String, metricSystem: Boolean, source: InputStream): String =
        kFitDataClassHandler.convertToDataClass(fileName, metricSystem, source).let { fitData ->
            ProtoBuf.encodeToHexString(fitData)
        }

    /**
     * Converts a FIT file to a Protobuf byte array.
     * @param fileName The name of the file being converted.
     * @param metricSystem Whether to use the metric or imperial system for metrics. MeasurementUtils is used to determine this.
     * @param source The FIT file to convert as an InputStream.
     * @return The Protobuf byte array.
     */
    fun convertFitToProtobufByteArray(fileName: String, metricSystem: Boolean, source: InputStream): ByteArray =
        kFitDataClassHandler.convertToDataClass(fileName, metricSystem, source).let { fitData ->
            ProtoBuf.encodeToByteArray(fitData)
        }

    /**
     * Converts a Protobuf hex string to a FIT file data class.
     * @param protoBuf The Protobuf hex string to convert.
     * @return The FIT file data class.
     */
    fun convertProtobufHexToFit(protoBuf: String): FitFileData =
        ProtoBuf.decodeFromHexString(protoBuf)

    /**
     * Converts a Protobuf byte array to a FIT file data class.
     * @param protoBuf The Protobuf byte array to convert.
     * @return The FIT file data class.
     */
    fun convertProtobufByteArrayToFit(protoBuf: ByteArray): FitFileData =
        ProtoBuf.decodeFromByteArray(protoBuf)

    /**
     * Gets the Protobuf schema for the FIT file data class.
     * At the moment this outputs a proto2 schema, but this will be updated to proto3 in the future.
     * @see ProtoBufSchemaGenerator
     * @see FitFileData
     * @see ProtoBuf
     * @return The Protobuf schema as a String.
     */
    fun getProtoBufSchema(protoVersion: ProtoBufSchemaGenerator.ProtoVersion) = generateProtoBufSchema {
        descriptor(FitFileData.serializer().descriptor) // Add root serializable descriptor
        descriptor(TestClass.serializer().descriptor)
        protoVersion(protoVersion) // Set protocol buffers version
    }
}