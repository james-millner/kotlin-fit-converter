package kjm.fit.converter

import kjm.fit.converter.out.models.FitFileData
import kotlinx.serialization.*
import kotlinx.serialization.protobuf.ProtoBuf
import java.io.InputStream

class KFitProtobufHandler {

    private val kFitConverter = KFitConverter()


    fun convertFitToProtobufHexString(fileName: String, metricSystem: Boolean, source: InputStream): String =
        kFitConverter.convert(fileName, metricSystem, source).let { fitData ->
            ProtoBuf.encodeToHexString(fitData)
        }

    fun convertFitToProtobufByteArray(fileName: String, metricSystem: Boolean, source: InputStream): ByteArray =
        kFitConverter.convert(fileName, metricSystem, source).let { fitData ->
            ProtoBuf.encodeToByteArray(fitData)
        }

    fun convertProtobufHexToFit(protoBuf: String): FitFileData =
        ProtoBuf.decodeFromHexString(protoBuf)

    fun convertProtobufByteArrayToFit(protoBuf: ByteArray): FitFileData =
        ProtoBuf.decodeFromByteArray(protoBuf)
}