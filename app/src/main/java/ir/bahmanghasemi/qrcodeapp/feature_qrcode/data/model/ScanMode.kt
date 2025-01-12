package ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ScanMode(
    val scanSource: ScanSource,
    val turnFlashlight: Boolean = false
)
