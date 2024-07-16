package kjm.fit.converter.converters

import kjm.fit.converter.out.models.Location
import kjm.fit.converter.out.models.LocationRecord
import com.garmin.fit.RecordMesg as FitLocationData

/**
 * Converter for converting RecordMesg to LocationRecord.
 * RecordMesg has been aliased to FitLocationData to avoid confusion.
 * @see <a href="https://developer.garmin.com/fit/protocol/#record">RecordMesg</a>
 * @see LocationRecord
 */
internal class FitLocationDataConverter: Converter<FitLocationData, LocationRecord> {

    private val SCALING_FACTOR = 11930465.0

    override fun convert(source: FitLocationData): LocationRecord =
        LocationRecord(
            timestamp = source.timestamp.date.toInstant().toString(),
            heartRate = source.heartRate?.toDouble(),
            cadence = source.cadence?.toDouble(),
            power = source.power?.toDouble(),
            temperature = source.temperature?.toDouble(),
            speed = source.speed?.toDouble(),
            elevation = source.altitude?.toDouble(),
            distance = source.distance?.toDouble(),
            zone = source.zone,
            location = source.positionLat?.let {
                Location(
                    gpsAccuracy = source.gpsAccuracy?.toDouble(),
                    grade = source.grade?.toDouble(),
                    latitude = source.positionLat.div(SCALING_FACTOR),
                    longitude = source.positionLong.div(SCALING_FACTOR),
                    altitude = source.altitude,
                )
            }
        )
}