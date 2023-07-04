package kjm.fit.converter.converters

import com.garmin.fit.EventMesg
import kjm.fit.converter.out.FitEvent


class FitEventMesgConverter: Converter<EventMesg, FitEvent> {
    override fun convert(source: EventMesg): FitEvent =
        FitEvent(
            timestamp = source.timestamp?.date.toString(),
            frontGearNum = source.frontGearNum?.toInt() ?: 0,
            rearGearNum = source.rearGearNum?.toInt() ?: 0,
        )
}