package kjm.fit.converter.converters

import kjm.fit.converter.out.models.FitFileData
import kjm.fit.converter.wrappers.FitDataWrapper

/**
 * A converter for converting a [FitDataWrapper] to a [FitFileData].
 * @see FitDataWrapper
 * @see FitFileData
 * @see Converter
 */
internal class FitDataWrapperConverter : Converter<FitDataWrapper, FitFileData> {
    override fun convert(source: FitDataWrapper): FitFileData {

        val (fileName, metricSystem, session, events, products, records) = source

        return FitFileData(
            activityName = fileName,
            averageCadence = session?.avgCadence?.toDouble(),
            averageHR = session?.avgHeartRate?.toDouble(),
            maxHR = session?.maxHeartRate?.toDouble(),
            averagePower = session?.avgPower?.toDouble(),
            maxPower = session?.maxPower?.toDouble(),
            averageSpeed = getDurationInRequestedUnit(session?.avgSpeed, metricSystem),
            maxSpeed = getDurationInRequestedUnit(session?.maxSpeed, metricSystem),
            averageCalories = session?.totalCalories?.toDouble(),
            averageTemperature = getTemperatureInRequestedUnit(session?.avgTemperature?.toDouble() ?: 0.0, metricSystem),
            activityStartDateTime = session?.startTime?.date?.toInstant().toString(),
            totalDistance = getDistanceInRequestedUnit(session?.totalDistance, metricSystem), // convert to km
            totalAscent = session?.totalAscent?.let { getElevationInRequestedUnit(it, metricSystem) },
            totalDescent = session?.totalDescent?.let { getElevationInRequestedUnit(it, metricSystem) },
            sport = session?.sport.toString(),
            productsUsed = products.toSet(),
            events = events,
            activityRecords = records,
        )
    }

    private fun getDurationInRequestedUnit(speedUnit: Float?, requestedUnit: String) = when (requestedUnit.lowercase()) {
        "imperial" -> speedUnit?.div(1609.34)?.times(3600) // Convert to MPH
        else -> speedUnit?.div(1000.0)?.times(3600) // Convert to KMH (default)
    }
    private fun getDistanceInRequestedUnit(speedUnit: Float?, requestedUnit: String) = when (requestedUnit.lowercase()) {
        "imperial" -> speedUnit?.div(1609.34) // Convert to Miles
        else -> speedUnit?.div(1000.0) // Convert to Kilometers
    }

    private fun getElevationInRequestedUnit(heightUnit: Int, requestedUnit: String) = when (requestedUnit.lowercase()) {
        "imperial" -> heightUnit.times(3.28084) // Convert to Feet
        else -> heightUnit.toDouble()// Convert to Meters
    }

    private fun getTemperatureInRequestedUnit(temperature: Double, requestedUnit: String) = when (requestedUnit.lowercase()) {
        "imperial" -> (temperature * 9 / 5) + 32 // Convert to Farenheit
        else -> temperature// Celsius
    }
}