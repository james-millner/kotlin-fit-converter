package kjm.fit.converter.out.models

import com.glureau.k2pb.annotation.ProtoMessage
import kotlinx.serialization.Serializable

/**
 * A data class representing various products used to record the fit file. Still in progress of being fleshed out.
 * @param productName The name of the product.
 * @param productDataConnection The data connection type of the product.
 * @param manufacturer The manufacturer of the product.
 */
@Serializable
@ProtoMessage
data class FitProduct(
    val productName: String,
    val productDataConnection: String?, // sourceType
    val manufacturer: String,
    val description: String?,
)