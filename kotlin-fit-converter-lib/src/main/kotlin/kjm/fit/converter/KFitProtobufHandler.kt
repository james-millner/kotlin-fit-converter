package kjm.fit.converter

import kjm.fit.converter.file.KFitFileToDataClassHandler
import kjm.fit.converter.out.models.FitFileData
import kotlinx.serialization.*
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream

class KFitProtobufHandler {

    private val kFitFileToDataClassHandler = KFitFileToDataClassHandler()

    /**
     * Converts a FIT file to a Protobuf hex string.
     * @param fileName The name of the file being converted.
     * @param metricSystem Whether the file is in metric or imperial units.
     * @param source The FIT file to convert.
     * @return The Protobuf hex string.
     */
    fun convertFitToProtobufHexString(fileName: String, metricSystem: Boolean, source: InputStream): String =
        kFitFileToDataClassHandler.convert(fileName, metricSystem, source).let { fitData ->
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
        kFitFileToDataClassHandler.convert(fileName, metricSystem, source).let { fitData ->
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