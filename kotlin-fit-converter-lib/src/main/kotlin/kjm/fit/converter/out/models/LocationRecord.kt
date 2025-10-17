package kjm.fit.converter.out.models

import com.glureau.k2pb.annotation.ProtoMessage
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
 * @param distance Distance captured in meters.
 * @param zone Zone captured.
 */
@Serializable
@ProtoMessage
data class LocationRecord(
    val heartRate: Double?,
    val cadence: Double?,
    val power: Double?,
    val location: Location?,
    val temperature: Double?,
    val speed: Double?,
    val elevation: Double?,
    val timestamp: String,
    val distance: Double?,
    val zone: Short?,
)

/**
 * Internal representation of location data.
 * @param latitude Latitude of the location.
 * @param longitude Longitude of the location.
 * @param altitude Altitude of the location.
 * @param grade Grade of the location.
 * @param gpsAccuracy GPS accuracy of the location.
 */
@Serializable
@ProtoMessage
data class Location(
    val latitude: Double,
    val longitude: Double,
    val altitude: Float?,
    val grade: Double?,
    val gpsAccuracy: Double?,
)