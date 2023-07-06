package kjm.fit.converter.wrappers

import com.garmin.fit.SessionMesg
import kjm.fit.converter.out.models.ActivityRecord
import kjm.fit.converter.out.models.FitEvent
import kjm.fit.converter.out.models.FitProduct

data class FitDataWrapper(
    val fitFileName: String,
    val metricSystem: String,
    val session: SessionMesg?,
    val events: Set<FitEvent>,
    val products: Set<FitProduct>,
    val records: Set<ActivityRecord>,
)