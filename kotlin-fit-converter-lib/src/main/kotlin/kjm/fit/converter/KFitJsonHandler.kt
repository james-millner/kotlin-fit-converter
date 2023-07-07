package kjm.fit.converter

import kjm.fit.converter.out.models.FitFileData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.InputStream

/**
 * Handles the conversion of FIT files to JSON and vice versa.
 * @constructor Creates a new instance of KFitJsonHandler.
 * @property kFitDataClassHandler The KFitDataClassHandler instance to use.
 * @see KFitDataClassHandler
 * @see FitFileData
 * @see Json
 * @see kotlinx.serialization
 * @see kotlinx.serialization.json
 */
class KFitJsonHandler {

    private val kFitDataClassHandler = KFitDataClassHandler()

    /**
     * Converts a FIT file to a JSON string.
     * @param fileName The name of the file being converted.
     * @param metricSystem Whether the file is in metric or imperial units.
     * @param source The FIT file to convert.
     * @return The JSON string.
     */
    fun convertFitToJSON(fileName: String, metricSystem: Boolean, source: InputStream): String {

        val fitData = kFitDataClassHandler.convert(fileName, metricSystem, source)
        return Json.encodeToString(FitFileData.serializer(), fitData)
    }

    /**
     * Converts a JSON string to a FIT file data class.
     * @param jsonString The JSON string to convert.
     * @return The FIT file data class.
     */
    fun convertJSONToFitData(jsonString: String): FitFileData {
        return Json.decodeFromString<FitFileData>(jsonString)
    }
}