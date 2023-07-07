package kjm.fit.converter

import kjm.fit.converter.out.models.FitFileData
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
class KFitProtobufHandler {

    private val kFitDataClassHandler = KFitDataClassHandler()

    /**
     * Converts a FIT file to a Protobuf hex string.
     * @param fileName The name of the file being converted.
     * @param metricSystem Whether the file is in metric or imperial units.
     * @param source The FIT file to convert.
     * @return The Protobuf hex string.
     */
    fun convertFitToProtobufHexString(fileName: String, metricSystem: Boolean, source: InputStream): String =
        kFitDataClassHandler.convert(fileName, metricSystem, source).let { fitData ->
            ProtoBuf.encodeToHexString(fitData)
        }

    /**
     * Converts a FIT file to a Protobuf byte array.
     * @param fileName The name of the file being converted.
     * @param metricSystem Whether the file is in metric or imperial units.
     * @param source The FIT file to convert.
     * @return The Protobuf byte array.
     */
    fun convertFitToProtobufByteArray(fileName: String, metricSystem: Boolean, source: InputStream): ByteArray =
        kFitDataClassHandler.convert(fileName, metricSystem, source).let { fitData ->
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
}