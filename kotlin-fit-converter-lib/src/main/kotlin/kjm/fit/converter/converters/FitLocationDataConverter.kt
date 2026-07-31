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

    private companion object {
        /** FIT stores coordinates in semicircles, where 2^31 semicircles is 180 degrees. */
        const val SEMICIRCLES_PER_DEGREE = 11930465.0
    }

    override fun convert(source: FitLocationData): LocationRecord =
        LocationRecord(
            timestamp = source.timestamp?.date?.toInstant()?.toString().orEmpty(),
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
                    latitude = source.positionLat.div(SEMICIRCLES_PER_DEGREE),
                    longitude = source.positionLong.div(SEMICIRCLES_PER_DEGREE),
                    altitude = source.altitude,
                )
            }
        )
}