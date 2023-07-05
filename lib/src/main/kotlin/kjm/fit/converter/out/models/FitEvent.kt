package kjm.fit.converter.out.models

import kotlinx.serialization.Serializable

@Serializable
data class FitEvent(
    val timestamp: String?,
    val frontGearNum: Int?,
    val rearGearNum: Int?,
)
