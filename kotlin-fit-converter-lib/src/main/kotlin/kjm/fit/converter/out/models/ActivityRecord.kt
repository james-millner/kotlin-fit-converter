package kjm.fit.converter.out.models

import kotlinx.serialization.Serializable

/**
 * A data class representing an event during an activity.
 * @param heartRate HR captured.
 * @param cadence Cadence captured.
 * @param power Power captured.
 * @param location Location Object captured.
 * @param temperature Temperature captured.
 * @param speed Speed captured.
 * @param elevation Elevation captured.
 * @param timestamp Timestamp of the event.
 */
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

/**
 * Internal representation of location data.
 * @param latitude Latitude of the location.
 * @param longitude Longitude of the location.
 * @param altitude Altitude of the location.
 */
@Serializable
data class Location(
    val latitude: Double,
    val longitude: Double,
    val altitude: Float?,
)