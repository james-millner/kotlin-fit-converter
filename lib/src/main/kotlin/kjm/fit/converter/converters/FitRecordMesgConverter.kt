package kjm.fit.converter.converters

import com.garmin.fit.RecordMesg
import kjm.fit.converter.out.ActivityRecord
import kjm.fit.converter.out.Location

class FitRecordMesgConverter: Converter<RecordMesg, ActivityRecord> {
    override fun convert(source: RecordMesg): ActivityRecord =
        ActivityRecord(
            timestamp = source.timestamp.date.toString(),
            heartRate = source.heartRate?.toDouble(),
            cadence = source.cadence?.toDouble(),
            power = source.power?.toDouble(),
            temperature = source.temperature?.toDouble(),
            speed = source.speed?.toDouble(),
            elevation = source.altitude?.toDouble(),
            location = source.positionLat?.let {
                Location(
                    latitude = source.positionLat.div(11930465.0),
                    longitude = source.positionLong.div(11930465.0),
                    altitude = source.altitude
                )
            }
        )
}