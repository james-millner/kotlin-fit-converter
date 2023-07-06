package kjm.fit.converter

import kjm.fit.converter.out.models.FitFileData
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.InputStream

class KFitJsonHandler {

    private val kFitConverter = KFitConverter()

    fun convertFitToJSON(fileName: String, metricSystem: Boolean, source: InputStream): String {

        val fitData = kFitConverter.convert(fileName, metricSystem, source)
        return Json.encodeToString(FitFileData.serializer(), fitData)
    }

    fun convertJSONToFitData(jsonString: String): FitFileData {
        return Json.decodeFromString<FitFileData>(jsonString)
    }
}