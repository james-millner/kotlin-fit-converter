package kjm.fit.converter

import kjm.fit.converter.out.models.FitFileData
import kotlinx.serialization.json.Json
import java.io.InputStream

class KFitJsonHandler {

    private val json = Json { prettyPrint = false }
    private val kFitConverter = KFitConverter()

    fun convertFitToJSON(fileName: String, metricSystem: Boolean, source: InputStream): String {

        val fitData = kFitConverter.convert(fileName, metricSystem, source)
        return json.encodeToString(FitFileData.serializer(), fitData)
    }
}