package kjm.fit.converter.converters

import com.garmin.fit.RecordMesg
import kjm.fit.converter.out.models.LocationRecord
import kjm.fit.converter.out.models.Location

/**
 * Converter for converting RecordMesg to LocationRecord.
 * @see <a href="https://developer.garmin.com/fit/protocol/#record">RecordMesg</a>
 * @see LocationRecord
 */
internal class FitRecordMesgConverter: Converter<RecordMesg, LocationRecord> {
    override fun convert(source: RecordMesg): LocationRecord =
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
                    latitude = source.positionLat.div(11930465.0),
                    longitude = source.positionLong.div(11930465.0),
                    altitude = source.altitude,
                )
            }
        )
}