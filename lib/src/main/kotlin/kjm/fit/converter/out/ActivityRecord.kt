package kjm.fit.converter.out

import kotlinx.serialization.Serializable

@Serializable
data class ActivityRecord(
    val heartRate: Double?,
    val cadence: Double?,
    val power: Double?,
    val location: Location?,
    val temperature: Double?,
    val speed: Double?,
    val elevation: Double?,
    val timestamp: String,
)

@Serializable
data class Location(
    val latitude: Double,
    val longitude: Double,
    val altitude: Float?,
)