package kjm.fit.converter.out.models

import kotlinx.serialization.Serializable

/**
 * A data class representing the entire FIT file. This is an opinionated view on interesting data points and is subject to change.
 * It's an effort to collate as much valuable data from as many activity types as possible. Initially though this has scope for cycling and running primarily.
 * @param activityName The name of the activity.
 * @param activityStartDateTime The start date and time of the activity.
 * @param sport The sport of the activity.
 * @param averageHR Average HR of the activity.
 * @param maxHR Max HR of the activity.
 * @param averageCadence Average cadence of the activity.
 * @param averagePower Average power of the activity.
 * @param maxPower Max power of the activity.
 * @param averageCalories Average calories of the activity.
 * @param averageSpeed Average speed of the activity.
 * @param maxSpeed Max speed of the activity.
 * @param averageTemperature Average temperature of the activity.
 * @param totalAscent Total ascent of the activity.
 * @param totalDescent Total descent of the activity.
 * @param totalDistance Total distance of the activity.
 * @param productsUsed Products used during the activity.
 * @param events Events during the activity.
 * @param locationRecords Activity records during the activity.
 * @see FitProduct
 * @see FitEvent
 * @see LocationRecord
 * @see Location
 */
@Serializable
data class FitFileData(
    val activityName: String,
    val activityStartDateTime: String,
    val sport: String,
    val averageHR: Double?,
    val maxHR: Double?,
    val averageCadence: Double?,
    val maxCadence: Double?,
    val averagePower: Double?,
    val maxPower: Double?,
    val averageCalories: Double?,
    val averageSpeed: Double?,
    val maxSpeed: Double?,
    val averageTemperature: Double?,
    val totalAscent: Int?,
    val totalDescent: Int?,
    val totalDistance: Double?,
    val productsUsed: Set<FitProduct> = emptySet(),
    val events: Set<FitEvent> = emptySet(),
    val locationRecords: Set<LocationRecord> = emptySet(),
)