package kjm.fit.converter.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

/**
 * A suite of tests for the [MeasurementUtils] class.
 * @see MeasurementUtils
 * @since 0.2.1
 */
class MeasurementUtilsTest {

    private val measurementUtils = MeasurementUtils()

    @ParameterizedTest(name = "Celsius {0} should be equal to Fahrenheit {1}")
    @CsvSource("25.0, 77.0", "9.0, 48.2", "18, 64.4")
    @DisplayName("Celsius to Fahrenheit Conversion")
    fun celsiusToFahrenheit(celsius: Double, fahrenheit: Double) {
        assertEquals(fahrenheit, measurementUtils.temperatureInRequestedUnit(celsius, MeasurementUnit.IMPERIAL))
    }

    @ParameterizedTest(name = "Meters per second (m/s) {0} should be equal to Miles per hour (MPH) {1}")
    @CsvSource("7.5, 16.8", "25.0, 56.0", "18, 40.3", "26.0, 58.2")
    @DisplayName("Meters per second (m/s) to Miles per hour (MPH) Conversion")
    fun metersPerSecondToMph(metersPerSecond: Float, mph: Double) {
        assertEquals(mph, measurementUtils.durationInRequestedUnit(metersPerSecond, MeasurementUnit.IMPERIAL))
    }

    @ParameterizedTest(name = "Meters per second (m/s) {0} should be equal to Kilometers per hour (KMH) {1}")
    @CsvSource("7.5, 27.0", "25.0, 90.0", "18, 64.8", "26.0, 93.7")
    @DisplayName("Meters per second (m/s) to Kilometers per hour (KMH) Conversion")
    fun metersPerSecondToKmh(metersPerSecond: Float, kmh: Double) {
        assertEquals(kmh, measurementUtils.durationInRequestedUnit(metersPerSecond, MeasurementUnit.METRIC))
    }

    @ParameterizedTest(name = "Meters {0} should be equal to Kilometers {1}")
    @CsvSource("7500, 7.5", "25000, 25.0", "18000, 18.0", "26000, 26.0")
    @DisplayName("Meters to Kilometers Conversion")
    fun metersToKilometers(metersPerSecond: Float, kms: Double) {
        assertEquals(kms, measurementUtils.distanceInRequestedUnit(metersPerSecond, MeasurementUnit.METRIC))
    }

    @ParameterizedTest(name = "Meters {0} should be equal to Miles {1}")
    @CsvSource("7500, 4.67", "25000, 15.54", "18000, 11.19", "26000, 16.16")
    @DisplayName("Meters to Miles Conversion")
    fun metersToMiles(metersPerSecond: Float, miles: Double) {
        assertEquals(miles, measurementUtils.distanceInRequestedUnit(metersPerSecond, MeasurementUnit.IMPERIAL))
    }

    @ParameterizedTest(name = "Meters {0} should be equal to Foot {1}")
    @CsvSource("25, 82", "250, 820", "1800, 5906", "2600, 8530")
    @DisplayName("Meters to Foot Conversion")
    fun metersToFeet(elevation: Int, foot: Int) {
        assertEquals(foot, measurementUtils.elevationInRequestedUnit(elevation, MeasurementUnit.IMPERIAL))
    }
}