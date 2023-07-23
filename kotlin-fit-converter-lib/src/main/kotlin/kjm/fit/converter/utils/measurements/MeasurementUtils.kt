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

    private val one_meter_in_feet = 3.28084
    private val one_meter_in_miles = 0.000621371
    private val one_kilometer_in_meters = 1000.0
    private val mph_conversion_factor = 2.23694
    private val kmh_conversion_factor = 3.6
    private val ten = 10
    private val one_hundred = 100

    /**
     * Converts the given meters per second to the requested unit
     * @param metersPerSecond The meters per second to convert originally provided from Garmin FIT
     * @param requestedUnit Allows passing IMPERIAL to convert to miles per hour, or METRIC to get kilometers per hour.
     * @return The converted value
     */
    fun durationInRequestedUnit(metersPerSecond: Float, requestedUnit: MeasurementUnit): Double {
        return when (requestedUnit) {
            MeasurementUnit.IMPERIAL -> ceil(metersPerSecond * mph_conversion_factor * ten) / ten
            else -> ceil(metersPerSecond * kmh_conversion_factor * ten) / ten
        }
    }

    /**
     * Converts the given meters per second to the requested unit
     * @param meters The meters to convert originally provided from Garmin FIT
     * @param requestedUnit Allows passing IMPERIAL to convert to miles, or METRIC to keep the value as is.
     * @return The converted value
     */
    fun distanceInRequestedUnit(meters: Float, requestedUnit: MeasurementUnit): Double {
        return when (requestedUnit) {
            MeasurementUnit.IMPERIAL -> ceil(meters * one_meter_in_miles * one_hundred) / one_hundred
            else -> ceil(meters / one_kilometer_in_meters * one_hundred) / one_hundred
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
            MeasurementUnit.IMPERIAL -> (meters * one_meter_in_feet).roundToInt()
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
