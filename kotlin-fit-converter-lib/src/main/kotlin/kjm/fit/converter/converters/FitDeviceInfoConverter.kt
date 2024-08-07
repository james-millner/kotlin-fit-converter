package kjm.fit.converter.converters

import com.garmin.fit.FaveroProduct
import com.garmin.fit.GarminProduct
import com.garmin.fit.Manufacturer
import kjm.fit.converter.out.models.FitProduct
import com.garmin.fit.DeviceInfoMesg as FitDeviceInfoData

/**
 * Converter for converting DeviceInfoMesg to FitProduct.
 * DeviceInfoMesg has been aliased to FitDeviceInfoData to avoid confusion.
 * @see <a href="https://developer.garmin.com/fit/protocol/#device-information">DeviceInfoMesg</a>
 * @see FitProduct
 */
internal class FitDeviceInfoConverter: Converter<FitDeviceInfoData, FitProduct> {
    override fun convert(source: FitDeviceInfoData): FitProduct {
        val productName = getProductName(source)
        return FitProduct(
            productName = productName,
            productDataConnection = source.sourceType?.toString(),
            manufacturer = Manufacturer.getStringFromValue(source.manufacturer),
            description = source.descriptor?.toString()
        )
    }

    private fun getProductName(source: FitDeviceInfoData): String =
        if(source.garminProduct != null) {
            var garminProductName = GarminProduct.getStringFromValue(source.garminProduct)
            garminProductName.ifEmpty {
                source?.productName ?: "Unknown"
            }
        } else if(source.faveroProduct != null) {
            FaveroProduct.getStringFromValue(source.faveroProduct)
        } else {
            source?.productName ?: "Unknown"
        }
}