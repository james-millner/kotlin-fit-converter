package kjm.fit.converter.out

import kotlinx.serialization.Serializable

@Serializable
data class FitProduct(
    val productName: String,
    val productDataConnection: String?, // sourceType
    val manufacturer: String,
    val unknownProduct: Boolean,
    val antDeviceNumber: Long?,
    val bodyLocation: String?
)