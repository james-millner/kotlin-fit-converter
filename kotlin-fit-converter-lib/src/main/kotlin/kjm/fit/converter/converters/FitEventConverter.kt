package kjm.fit.converter.converters

import kjm.fit.converter.out.models.FitEvent
import com.garmin.fit.EventMesg as FitEventData

/**
 * Converter for converting EventMesg to FitEvent.
 * EventMesg has been aliased to FitEventData to avoid confusion.
 * @see <a href="https://developer.garmin.com/fit/protocol/#event">EventMesg</a>
 * @see FitEvent
 */
internal class FitEventConverter: Converter<FitEventData, FitEvent> {
    override fun convert(source: FitEventData): FitEvent =
        FitEvent(
            timestamp = source.timestamp.date.toInstant().toString(),
            eventType = source.eventType?.toString(),
            eventName = source.event.name,
            frontGearNum = source.frontGearNum?.toInt(),
            rearGearNum = source.rearGearNum?.toInt(),
        )
}