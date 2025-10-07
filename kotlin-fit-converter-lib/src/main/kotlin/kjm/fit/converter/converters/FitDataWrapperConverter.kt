package kjm.fit.converter.converters

import kjm.fit.converter.out.models.FitFileData
import kjm.fit.converter.utils.measurements.MeasurementUtils
import kjm.fit.converter.wrappers.FitDataWrapper

/**
 * A converter for converting a [FitDataWrapper] to a [FitFileData].
 * @see FitDataWrapper
 * @see FitFileData
 * @see Converter
 */
internal class FitDataWrapperConverter : Converter<FitDataWrapper, FitFileData> {

    private val measurementUtils = MeasurementUtils()

    override fun convert(source: FitDataWrapper): FitFileData {

        val (fileName, metricSystem, session, events, products, records) = source

        return FitFileData(
            activityName = fileName,
            averageCadence = session.avgCadence?.toDouble(),
            maxCadence = session.maxCadence?.toDouble(),
            averageHR = session.avgHeartRate?.toDouble(),
            maxHR = session.maxHeartRate?.toDouble(),
            averagePower = session.avgPower?.toDouble(),
            maxPower = session.maxPower?.toDouble(),
            averageSpeed = measurementUtils.durationInRequestedUnit(session.avgSpeed, metricSystem),
            maxSpeed = measurementUtils.durationInRequestedUnit(session.maxSpeed, metricSystem),
            averageCalories = session.totalCalories?.toDouble(),
            averageTemperature = measurementUtils.temperatureInRequestedUnit(session.avgTemperature?.toDouble() ?: 0.0, metricSystem),
            activityStartDateTime = session.startTime?.date?.toInstant().toString(),
            totalDistance = measurementUtils.distanceInRequestedUnit(session.totalDistance, metricSystem), // convert to km
            totalAscent = session.totalAscent?.let { measurementUtils.elevationInRequestedUnit(it, metricSystem) },
            totalDescent = session.totalDescent?.let { measurementUtils.elevationInRequestedUnit(it, metricSystem) },
            sport = session.sport.toString(),
            productsUsed = products.toSet(),
            events = events,
            locationRecords = records,
        )
    }
}