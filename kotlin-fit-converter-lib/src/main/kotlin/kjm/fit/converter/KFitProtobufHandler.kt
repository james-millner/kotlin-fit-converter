package kjm.fit.converter

import com.glureau.k2pb.runtime.K2PB
import com.glureau.k2pb.runtime.decodeFromByteArray
import com.glureau.k2pb.runtime.encodeToByteArray
import kjm.fit.converter.out.models.FitFileData
import kjm.fit.converter.out.models.registerKotlinFitConverterLibCodecs
import java.io.InputStream

/**
 * Handles the conversion of FIT files to Protobuf and vice versa.
 * @constructor Creates a new instance of KFitProtobufHandler.
 * @property kFitDataClassHandler The KFitDataClassHandler instance to use.
 * @see KFitDataClassHandler
 * @see FitFileData
 */
class KFitProtobufHandler(
    metricSystem: Boolean = true
) {

    private val kFitDataClassHandler = KFitDataClassHandler(metricSystem)

    private val serializer = K2PB {
        registerKotlinFitConverterLibCodecs()
    }

    /**
     * Converts a FIT file to a Protobuf hex string.
     * @param fileName The name of the file being converted.
     * @param source The FIT file to convert as an InputStream.
     * @return The Protobuf hex string.
     */
    @OptIn(ExperimentalStdlibApi::class) // toHexString
    fun convertFitToProtobufHexString(fileName: String, source: InputStream): String =
        kFitDataClassHandler.convertToDataClass(fileName, source).let { fitData ->
            serializer.encodeToByteArray<FitFileData>(fitData)
                .joinToString("") { it.toHexString() }
        }

    /**
     * Converts a FIT file to a Protobuf byte array.
     * @param fileName The name of the file being converted.
     * @param source The FIT file to convert as an InputStream.
     * @return The Protobuf byte array.
     */
    fun convertFitToProtobufByteArray(fileName: String, source: InputStream): ByteArray =
        kFitDataClassHandler.convertToDataClass(fileName, source).let { fitData ->
            serializer.encodeToByteArray<FitFileData>(fitData)
        }

    /**
     * Converts a Protobuf hex string to a FIT file data class.
     * @param protoBuf The Protobuf hex string to convert.
     * @return The FIT file data class.
     */
    @OptIn(ExperimentalStdlibApi::class) // hexToByte
    fun convertProtobufHexToFit(protoBuf: String): FitFileData =
        protoBuf.chunked(2).map { it.hexToByte() }.toByteArray().let {
            serializer.decodeFromByteArray<FitFileData>(it)!!
        }

    /**
     * Converts a Protobuf byte array to a FIT file data class.
     * @param protoBuf The Protobuf byte array to convert.
     * @return The FIT file data class.
     */
    fun convertProtobufByteArrayToFit(protoBuf: ByteArray): FitFileData =
        serializer.decodeFromByteArray<FitFileData>(protoBuf)!!

    // proto file are generated at build time
}