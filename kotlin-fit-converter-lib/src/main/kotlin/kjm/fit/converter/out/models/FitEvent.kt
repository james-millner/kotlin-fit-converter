package kjm.fit.converter.out.models

import kotlinx.serialization.Serializable

/**
 * A data class for holding various event level data.
 * Initially this is scoped for Di2 gear changes. But will be expanded to include other events.
 * @param timestamp Timestamp of the event.
 * @param frontGearNum Front gear number.
 * @param rearGearNum Rear gear number.
 */
@Serializable
data class FitEvent(
    val timestamp: String?,
    val frontGearNum: Int?,
    val rearGearNum: Int?,
)
