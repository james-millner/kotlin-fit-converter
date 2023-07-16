package kjm.fit.converter.converters

import com.garmin.fit.EventMesg
import kjm.fit.converter.out.models.FitEvent

/**
 * Converter for converting EventMesg to FitEvent.
 * @see <a href="https://developer.garmin.com/fit/protocol/#event">EventMesg</a>
 * @see FitEvent
 */
internal class FitEventMesgConverter: Converter<EventMesg, FitEvent> {
    override fun convert(source: EventMesg): FitEvent =
        FitEvent(
            timestamp = source.timestamp.date.toInstant().toString(),
            eventType = source.eventType?.toString(),
            eventName = source.event.name,
            frontGearNum = source.frontGearNum?.toInt(),
            rearGearNum = source.rearGearNum?.toInt(),
        )
}