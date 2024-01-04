package kjm.fit.converter

import kjm.fit.converter.out.models.FitFileData
import kotlinx.serialization.ExperimentalSerializationApi
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
@ExperimentalSerializationApi
class KFitJsonHandler {

    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }
    private val kFitDataClassHandler = KFitDataClassHandler()

    /**
     * Converts a FIT file to a JSON string.
     * @param fileLocation The name of the file being converted.
     * @param metricSystem Whether to use the metric or imperial system for metrics. MeasurementUtils is used to determine this.
     * @param source The FIT file to convert as an InputStream.
     * @return The JSON string.
     */
    fun convertFitToJSON(fileLocation: String, metricSystem: Boolean, source: InputStream) =
        kFitDataClassHandler.convertToDataClass(fileLocation, metricSystem, source).let {
            json.encodeToString(FitFileData.serializer(), it)
        }


    /**
     * Converts a JSON string to a FIT file data class.
     * @param jsonString The JSON string to convert back into a FIT file data class.
     * @return The FIT file data class.
     * @see FitFileData
     */
    fun convertJSONToFitData(jsonString: String) = json.decodeFromString<FitFileData>(jsonString)
}