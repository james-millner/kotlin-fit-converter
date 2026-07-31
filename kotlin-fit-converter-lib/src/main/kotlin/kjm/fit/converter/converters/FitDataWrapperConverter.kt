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
            averageSpeed = session.avgSpeed?.let { measurementUtils.speedInRequestedUnit(it, metricSystem) },
            maxSpeed = session.maxSpeed?.let { measurementUtils.speedInRequestedUnit(it, metricSystem) },
            averageCalories = session.totalCalories?.toDouble(),
            averageTemperature = session.avgTemperature?.let { measurementUtils.temperatureInRequestedUnit(it.toDouble(), metricSystem) },
            activityStartDateTime = session.startTime?.date?.toInstant()?.toString().orEmpty(),
            totalDistance = session.totalDistance?.let { measurementUtils.distanceInRequestedUnit(it, metricSystem) }, // convert to km
            totalAscent = session.totalAscent?.let { measurementUtils.elevationInRequestedUnit(it, metricSystem) },
            totalDescent = session.totalDescent?.let { measurementUtils.elevationInRequestedUnit(it, metricSystem) },
            sport = session.sport?.toString().orEmpty(),
            activeTime = session.activeTime?.toDouble(),
            totalTimerTime = session.totalTimerTime?.toDouble(),
            productsUsed = products,
            events = events,
            locationRecords = records,
        )
    }
}