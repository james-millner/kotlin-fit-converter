package kjm.fit.converter.utils.measurements

import kotlin.math.ceil
import kotlin.math.roundToInt

/**
 * Enum to represent the measurement unit
 * @see MeasurementUtils
 */
enum class MeasurementUnit {
    IMPERIAL,
    METRIC
}

/**
 * Utility class to convert between metric and imperial units mostly.
 * Combines the default values from Garmin which are typically in metric, and converts them to imperial if requested.
 * @see MeasurementUtils
 * @see MeasurementUnit
 * @see ceil
 * @see kotlin.math
 */
class MeasurementUtils {

    private companion object {
        const val ONE_METER_IN_FEET = 3.28084
        const val ONE_METER_IN_MILES = 0.000621371
        const val ONE_KILOMETER_IN_METERS = 1000.0
        const val MPH_CONVERSION_FACTOR = 2.23694
        const val KMH_CONVERSION_FACTOR = 3.6
        const val ONE_DECIMAL_PLACE = 10
        const val TWO_DECIMAL_PLACES = 100
    }

    /**
     * Converts the given meters per second to the requested unit
     * @param metersPerSecond The meters per second to convert originally provided from Garmin FIT
     * @param requestedUnit Allows passing IMPERIAL to convert to miles per hour, or METRIC to get kilometers per hour.
     * @return The converted value, rounded up to one decimal place
     */
    fun speedInRequestedUnit(metersPerSecond: Float, requestedUnit: MeasurementUnit): Double {
        return when (requestedUnit) {
            MeasurementUnit.IMPERIAL -> ceil(metersPerSecond * MPH_CONVERSION_FACTOR * ONE_DECIMAL_PLACE) / ONE_DECIMAL_PLACE
            else -> ceil(metersPerSecond * KMH_CONVERSION_FACTOR * ONE_DECIMAL_PLACE) / ONE_DECIMAL_PLACE
        }
    }

    /**
     * Converts the given meters per second to the requested unit.
     * @param metersPerSecond The meters per second to convert originally provided from Garmin FIT
     * @param requestedUnit Allows passing IMPERIAL to convert to miles per hour, or METRIC to get kilometers per hour.
     * @return The converted value
     */
    @Deprecated(
        "Converts a speed, not a duration. Use speedInRequestedUnit instead.",
        ReplaceWith("speedInRequestedUnit(metersPerSecond, requestedUnit)")
    )
    fun durationInRequestedUnit(metersPerSecond: Float, requestedUnit: MeasurementUnit): Double =
        speedInRequestedUnit(metersPerSecond, requestedUnit)

    /**
     * Converts the given meters value to the requested unit
     * @param meters The meters to convert originally provided from Garmin FIT
     * @param requestedUnit Allows passing IMPERIAL to convert to miles, or METRIC to convert to kilometers.
     * @return The converted value, rounded up to two decimal places
     */
    fun distanceInRequestedUnit(meters: Float, requestedUnit: MeasurementUnit): Double {
        return when (requestedUnit) {
            MeasurementUnit.IMPERIAL -> ceil(meters * ONE_METER_IN_MILES * TWO_DECIMAL_PLACES) / TWO_DECIMAL_PLACES
            else -> ceil(meters / ONE_KILOMETER_IN_METERS * TWO_DECIMAL_PLACES) / TWO_DECIMAL_PLACES
        }
    }

    /**
     * Converts the given meters value to the requested unit
     * @param meters The meters to convert originally provided from Garmin FIT
     * @param requestedUnit Allows passing IMPERIAL to convert to feet, or METRIC to keep the value as is.
     * @return The converted value
     */
    fun elevationInRequestedUnit(meters: Int, requestedUnit: MeasurementUnit): Int {
        return when (requestedUnit) {
            MeasurementUnit.IMPERIAL -> (meters * ONE_METER_IN_FEET).roundToInt()
            else -> meters
        }
    }

    /**
     * Converts the given temperature value to the requested unit
     * @param temperature The temperature to convert in Celsius provided from Garmin FIT by default
     * @param requestedUnit Allows passing IMPERIAL to convert to Fahrenheit, or METRIC to keep the value as is.
     * @return The converted value
     */
    fun temperatureInRequestedUnit(temperature: Double, requestedUnit: MeasurementUnit): Double {
        return when (requestedUnit) {
            MeasurementUnit.IMPERIAL -> convertTemperatureToImperial(temperature)
            else -> temperature
        }
    }

    private fun convertTemperatureToImperial(temperature: Double): Double {
        return (temperature * 9 / 5) + 32
    }
}
