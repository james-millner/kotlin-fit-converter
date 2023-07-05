package kjm.fit.converter.out.models

import kotlinx.serialization.Serializable

@Serializable
data class FitFileData(
    val activityName: String,
    val activityStartDateTime: String,
    val sport: String,
    val averageHR: Double?,
    val maxHR: Double?,
    val averageCadence: Double?,
    val averagePower: Double?,
    val maxPower: Double?,
    val averageCalories: Double?,
    val averageSpeed: Double?,
    val maxSpeed: Double?,
    val averageTemperature: Double?,
    val totalAscent: Double?,
    val totalDescent: Double?,
    val totalDistance: Double?,
    val productsUsed: Set<FitProduct> = emptySet(),
    val events: Set<FitEvent> = emptySet(),
    val activityRecords: Set<ActivityRecord> = emptySet(),
)